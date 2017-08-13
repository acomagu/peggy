package me.acomagu.slock.models.repositories

import me.acomagu.slock.models.MessagePost

import scala.concurrent.{ExecutionContext, Future}

trait MessagePostRepository {
  def store(msg: MessagePost)(implicit ec: ExecutionContext): Future[Unit]
  def getAll()(implicit ec: ExecutionContext): Future[List[MessagePost]]
}

trait UsesMessagePostRepository {
  val messagePostRepository: MessagePostRepository
}