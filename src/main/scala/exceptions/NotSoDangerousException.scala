package exceptions

object NotSoDangerousException {
  def defaultMessage(message: Option[String], cause: Option[Throwable]) = {
    message match {
      case Some(m) => m
      case _ =>
        cause match {
          case Some(c) => c.toString
          case _ => ""
        }
    }

  }
}

class NotSoDangerousException(message: Option[String] = None, cause: Option[Throwable] = None) extends RuntimeException(NotSoDangerousException.defaultMessage(message,cause))
