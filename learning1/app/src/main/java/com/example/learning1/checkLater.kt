//data class ProductInfo(val priceInDollars: Double?)
//
//class Product {
//    fun getProductInfo(): ProductInfo? {
//        return ProductInfo(100.0)
//    }
//}
//
//// Rewrite this function
//fun Product.getPriceInEuros() = getProductInfo()?.priceInDollars?.let { convertToEuros(it) }
//
//fun convertToEuros(dollars: Double): Double? {
//    return null
//}
//
//fun main() {
//    val product = Product()
//    val priceInEuros = product.getPriceInEuros()
//
//    if (priceInEuros != null) {
//        println("Price in Euros: €$priceInEuros")
//        // Price in Euros: €85.0
//    } else {
//        println("Price information is not available.")
//    }
//}


// next problem:
/*open class Vehicle(val make: String){
    open fun drive(): String = "Driving a Vehicle"
}

class Car(make: String): Vehicle(make){
    override fun drive(): String = "Driving a Car"
}


in this example: the make:String in Car, is it being inherited from th e*/