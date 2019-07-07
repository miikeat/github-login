import javafx.collections.FXCollections
import javafx.scene.web.WebEngine
import javafx.scene.web.WebView
import javafx.stage.StageStyle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import org.w3c.dom.html.HTMLDocument
import tornadofx.*
import kotlin.contracts.contract
import kotlin.coroutines.EmptyCoroutineContext

class LoginController : Controller() {
    var engine : WebEngine? = null

    fun login(webview: WebView, callback : LoginController.() -> Unit) {

            engine = webview.engine
            engine?.load("https://adventofcode.com/2018/auth/login")
            engine?.setOnStatusChanged {
                if (engine?.location == "https://adventofcode.com/2018")
                    //TODO remove this event handler again
                    callback()
        }
    }

    fun loadDays(callback: (result: String) -> Unit) {
        engine!!.setOnStatusChanged {
            if (engine!!.location == "https://adventofcode.com/2018") {
                //TODO load list of days
                engine!!.load("https://adventofcode.com/2018/day/4/input")
                engine!!.documentProperty().addListener { observable, oldValue, newValue ->
                    if (newValue != null && (newValue as HTMLDocument).body != null) {
                        callback(newValue.body.textContent)
                    }

                }

            }
        }
    }

}


class LoginModal : View() {
    private val controller : LoginController by inject()
    var result = ""
    override val root = webview {
        val wv = this
        prefWidth = 1150.0
        controller.login(this) {
            loadDays { result ->
                this@LoginModal.result = result
                this@LoginModal.close()
            }

        }
    }
}

class MainWindow : View() {

    val data = FXCollections.observableArrayList(mutableListOf<String>())
    override val root = vbox {
        button("Login") {
            action {
                val modal = find<LoginModal>().apply { openModal(stageStyle = StageStyle.UTILITY, block=true)}
                data.addAll(modal.result.split("\n"))
                println("Modal Result ${modal.result}")

            }
        }
        listview(data)
    }
}

class HelloWorldApp : App() {
    override val primaryView = MainWindow::class

}
