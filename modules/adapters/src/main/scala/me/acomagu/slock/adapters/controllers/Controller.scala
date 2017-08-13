package me.acomagu.slock.adapters.controllers

import akka.http.scaladsl.server.Route
import me.acomagu.slock.adapters.controllers.posts.PostControllers


trait Controller {
  def execute: Route
}

object Controller {
  def routes: Route = PostControllers.execute
}