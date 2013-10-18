package de.erna.krautmail.collectors.imap

import akka.actor.FSM
import de.erna.krautmail.collectors.imap.adapters.JavaMailImapAdapter
import scala.util.{Failure, Success}

/**
 * @author Eros Candelaresi <eros@candelaresi.de>
 * @since 17.10.13 21:12
 *
 *        Collector that handle a single IMAP session.
 *
 */
class ImapCollector extends FSM[ImapCollector.State, ImapCollector.Data] {

  import ImapCollector._

  implicit val ec = context.dispatcher

  def createImapAdapter = new JavaMailImapAdapter

  val IMAP = createImapAdapter

  startWith(Idle, Uninitialized)

  when(Idle) {
    case Event(Connect(connectionInfo), Uninitialized) => {
      val initialContext = IMAP.createContext()

      IMAP.connect(connectionInfo, initialContext).onComplete({
        case Success(ctx: ImapConnectionContext) => self ! ConnectedSuccess(ctx)
        case Failure(ex) => self ! ConnectedFail(ex.getMessage)
      })

      goto(Connecting) using ConnectionData(connectionInfo, initialContext)
    }
  }

  when(Connecting) {
    case Event(ConnectedSuccess(nextContext), connectionData: ConnectionData) =>
      goto(Idle) using connectionData.copy(connectionContext = nextContext)
  }
}


object ImapCollector {

  /*    States    */
  sealed trait State

  case object Connecting extends State

  case object Idle extends State

  case object Busy extends State

  case object Disconnecting extends State

  case object Interrupted extends State

  case object Exiting extends State

  /*    Data    */
  sealed trait Data

  case object Uninitialized extends Data

  case class ConnectionData(connectionInfo: ImapConnectionInfo, connectionContext: ImapConnectionContext) extends Data

  /*    Messages    */
  case class Connect(connectionInfo: ImapConnectionInfo)

  case class ConnectedSuccess(nextContext: ImapConnectionContext)

  //TODO: ConnectedFail needs to carry a context too since it could carry important info for retry counts, etc
  case class ConnectedFail(reason: String)

  case object Disconnect

  case object Exit

}