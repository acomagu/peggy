package me.acomagu.slock.adapters.controllers.posts

import akka.http.scaladsl.server.Route

object PostControllers extends MixInPostMessageUseCase {
  def execute: Route = PostMessageController.execute
}