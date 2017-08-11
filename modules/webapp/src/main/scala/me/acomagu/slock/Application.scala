package me.acomagu.slock

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import akka.stream.ActorMaterializer
import me.acomagu.slock.adapters.controllers.Controller

import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._
import scala.io.StdIn

/**
  * Applicatinルート
  */
object Application {

  private val HOST = "localhost"

  private val PORT = 8080

  def main(args: Array[String]) {
    try {
      implicit val system = ActorSystem("syncle")
      implicit val materializer = ActorMaterializer()
      implicit val executionContext = system.dispatcher

      println("Application Start...")

      scalikejdbc.config.DBs.setupAll()

      println("Application Configuration loaded")

      val bind: Future[ServerBinding] = Http().bindAndHandle(Controller.routes, HOST, PORT)

      println(s"Application Started at $HOST:$PORT (Press Any Key if you want to terminate.)")

      // アプリケーション終了条件は何らかのキーがタイプされたら
      // あくまで簡易実装、本番配置時はcommons-daemonでも使ってデーモン化すべし
      StdIn.readLine()

      println("Application End...")

      bind
        .flatMap(_.unbind())
        .onComplete { _ =>
          Await.result(system.terminate(), 10.seconds)
        }

      println("Application End")
    } catch {
      case e: Throwable => println("Application has error", e)
    }
  }

}

