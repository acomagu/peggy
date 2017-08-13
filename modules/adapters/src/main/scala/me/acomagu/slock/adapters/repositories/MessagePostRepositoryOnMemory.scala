package me.acomagu.slock.adapters.repositories

import me.acomagu.slock.models.MessagePost
import me.acomagu.slock.models.repositories.MessagePostRepository

import scala.concurrent.{ExecutionContext, Future}

trait MessagePostRepositoryOnMemory extends MessagePostRepository {
  var msgs: List[MessagePost] = List[MessagePost]()
  override def store(msg: MessagePost)(implicit ec: ExecutionContext): Future[Unit] = {
    msgs :+= msg
    Future.successful()
  }
  override def getAll()(implicit ec: ExecutionContext): Future[List[MessagePost]] = {
    Future.successful(msgs)
  }
}

object MessagePostRepositoryOnMemoryImpl extends MessagePostRepositoryOnMemory

trait MixInMessagePostRepository {
  val messagePostRepository: MessagePostRepository = MessagePostRepositoryOnMemoryImpl
}
