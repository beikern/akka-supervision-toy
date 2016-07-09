package exceptions

class BoomException (message: Option[String] = None, cause: Option[Throwable] = None)  extends NotSoDangerousException (message, cause)
