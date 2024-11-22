package tat.mukhutdinov.lesson10

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import tat.mukhutdinov.lesson10.data.Datasource
import tat.mukhutdinov.lesson10.ui.DessertClickerApp
import tat.mukhutdinov.lesson10.ui.theme.DessertClickerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DessertClickerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding(),
                ) {
                    DessertClickerApp(
                        desserts = Datasource.dessertList,
                        onShareButtonClicked = { dessertsSold, revenue ->
                            shareSoldDessertsInformation(
                                intentContext = this,
                                dessertsSold = dessertsSold,
                                revenue = revenue
                            )
                        }
                    )
                }
            }
        }
    }

    /**
     * Share desserts sold information using ACTION_SEND intent
     */
    private fun shareSoldDessertsInformation(
        intentContext: Context,
        dessertsSold: Int,
        revenue: Int
    ) {
        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(
                Intent.EXTRA_TEXT,
                intentContext.getString(R.string.share_text, dessertsSold, revenue)
            )
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)

        try {
            ContextCompat.startActivity(intentContext, shareIntent, null)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                intentContext,
                intentContext.getString(R.string.sharing_not_available),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}


fun main() {

    val parent: (Int, Int) -> Unit = { a, b ->
        println("Parent is called with params $a and $b")
    }

    val child1: () -> Unit = {
        println("Child 1 is called")
        parent(1, 2)
    }


    val child2: () -> Unit = {
        println("Child 2 is called")
        child1()
    }

    child2()

}

