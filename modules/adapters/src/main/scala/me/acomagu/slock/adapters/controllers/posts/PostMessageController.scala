package me.acomagu.slock.adapters.controllers.posts

import akka.http.scaladsl.server.Directives._
import akka.actor.ActorSystem
import akka.http.scaladsl.model.{HttpResponse, StatusCodes}
import me.acomagu.slock.adapters.presenters.UnitPresenter
import me.acomagu.slock.adapters.repositories.MixInMessagePostRepository
import me.acomagu.slock.usecases.posts.{PostMessageUseCase, UsesPostMessageUseCase}

import scala.util.{Failure, Success}

trait PostMessageController extends UsesPostMessageUseCase {
  def execute =
    path("api" / "post") {
      post {
        formFields('msg.as[String]) { msg =>
          implicit val ec = ActorSystem("slock").dispatcher

          println("[START]" + this.getClass.getName)

          val result = UnitPresenter.execute(postMessageUseCase.execute(msg))
          onComplete(result) {
            case Success(_) =>
              complete(HttpResponse(status = StatusCodes.NoContent))
            case Failure(t) =>
              println("Error: ", t)
              complete(HttpResponse(status = StatusCodes.InternalServerError))
          }
        }
      }
    }
}

object PostMessageController extends PostMessageController with MixInPostMessageUseCase

trait MixInPostMessageUseCase {
  val postMessageUseCase = new PostMessageUseCase with MixInMessagePostRepository
}