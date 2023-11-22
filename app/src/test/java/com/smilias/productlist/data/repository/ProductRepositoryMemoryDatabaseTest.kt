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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner::class)
class ProductRepositoryMemoryDatabaseTest {

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
        productLocalDatasource = ProductLocalDatasourceMemoryImpl()
        productRepository = ProductRepositoryImpl(productLocalDatasource, productRemoteDatasource)
    }

    @Test
    fun `test getProductFromApi when data is available in db and refresh database`() = runBlocking {
        productLocalDatasource.saveProductsToDb(listOf(mockProduct1))
        `when`(productRemoteDatasource.getProducts()).thenReturn(Resource.Success(listOf(mockProduct2)))

        val result = productRepository.getProductsFromApi()

        assertTrue(result is Resource.Success)
        assertTrue(!result.data.isNullOrEmpty())
        assertEquals(result.data?.size, 1)
        assertEquals(result.data!![0], mockProduct2)

        val savedProduct = productLocalDatasource.getProductsFromDb()
        assertEquals(mockProduct2, savedProduct[0])

    }
}
