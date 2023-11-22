import com.smilias.productlist.common.Resource
import com.smilias.productlist.data.local.ProductDao
import com.smilias.productlist.data.mapper.toProduct
import com.smilias.productlist.data.remote.model.ProductDto
import com.smilias.productlist.data.remote.ProductApi
import com.smilias.productlist.data.repository.ProductRepositoryImpl
import com.smilias.productlist.data.repository.datasource.ProductLocalDatasource
import com.smilias.productlist.data.repository.datasource.ProductRemoteDatasource
import com.smilias.productlist.data.repository.datasourceImpl.ProductLocalDatasourceMemoryImpl
import com.smilias.productlist.domain.model.Product
import kotlinx.coroutines.runBlocking;
import org.junit.Assert;
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner;
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class ProductRepositoryImplTest {

    @Mock
    private lateinit var productLocalDatasource: ProductLocalDatasource

    @Mock
    private lateinit var productRemoteDatasource: ProductRemoteDatasource

    private lateinit var productRepository: ProductRepositoryImpl

    private val mockProduct1 = Product(
        id = 1,
        description = "desc",
        image = "/path",
        name = "Test product",
        price = "3.21",
        thumbnail = "/path_image"
    )
    private val mockProduct2 = Product(
        id = 2,
        description = "desc",
        image = "/path",
        name = "Test product",
        price = "3.21",
        thumbnail = "/path_image"
    )

    @Before
    fun setup() {
        productRepository = ProductRepositoryImpl(productLocalDatasource, productRemoteDatasource)
    }

    @Test
    fun `test getProductsFromDb when data is available`() = runBlocking {

        val mockProductList = listOf(mockProduct1)
        `when`(productLocalDatasource.getProductsFromDb()).thenReturn(mockProductList)

        val result = productRepository.getProducts()

        assertTrue(result is Resource.Success)
        assertTrue(!result.data.isNullOrEmpty())
        assertEquals(result.data?.size, mockProductList.size)
        assertEquals(result.data!![0], mockProductList[0])
    }

    @Test
    fun `test getProductsFromDb when data is not available`() = runBlocking {
        `when`(productLocalDatasource.getProductsFromDb()).thenReturn(emptyList())
        `when`(productRemoteDatasource.getProducts()).thenReturn(Resource.Success(listOf(mockProduct2)))

        val result = productRepository.getProducts()

        assertTrue(result is Resource.Success)
        assertTrue(!result.data.isNullOrEmpty())
        assertEquals(result.data?.size, 1)
        assertEquals(result.data!![0], mockProduct2)
    }

    @Test
    fun `test getProductFromApi when data is available in db and refresh database`() = runBlocking {
        productLocalDatasource.saveProductsToDb(listOf(mockProduct1))
        `when`(productRemoteDatasource.getProducts()).thenReturn(Resource.Success(listOf(mockProduct2)))

        val result = productRepository.getProductsFromApi()

        verify(productLocalDatasource).saveProductsToDb(listOf(mockProduct2))

        assertTrue(result is Resource.Success)
        assertTrue(!result.data.isNullOrEmpty())
        assertEquals(result.data?.size, 1)
        assertEquals(result.data!![0], mockProduct2)

    }

    @Test
    fun `test when getProductFromApi fails`() = runBlocking {
        val error = "Failed"
        `when`(productRemoteDatasource.getProducts()).thenReturn(Resource.Error(message = error))

        val result = productRepository.getProductsFromApi()

        assertTrue(result is Resource.Error)
        assertTrue(result.message == error)
    }

    @Test
    fun `test when api send null empty data`() = runBlocking {
        val error = "Api returned null data"
        `when`(productRemoteDatasource.getProducts()).thenReturn(Resource.Success(data = null))

        val result = productRepository.getProductsFromApi()

        assertTrue(result is Resource.Error)
        assertTrue(result.message == error)
    }

    @Test
    fun `test getProductById`() = runBlocking {
        val id = 1L
        `when`(productLocalDatasource.getProductFromDb(id)).thenReturn(mockProduct1)

        val result = productRepository.getProductById(id)
        assertEquals(result, mockProduct1)
    }
}
