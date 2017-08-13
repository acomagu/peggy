package me.acomagu.slock.usecases.posts

import me.acomagu.slock.contracts.{InputPort, UseCase}
import me.acomagu.slock.models.MessagePost
import me.acomagu.slock.models.repositories.UsesMessagePostRepository

import scala.concurrent.{ExecutionContext, Future}

trait PostMessageUseCase extends UseCase with InputPort[String, Unit] with UsesMessagePostRepository {
  override protected def call(arg: String)(implicit ec: ExecutionContext): Future[Unit] = {
    println(arg)
    val messagePost = MessagePost(arg)
    messagePostRepository.store(messagePost)
    Future.successful()
  }
}

trait UsesPostMessageUseCase {
  val postMessageUseCase: PostMessageUseCase
}