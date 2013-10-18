package de.erna.krautmail.collectors.imap

import akka.actor.{ActorLogging, Actor}

/**
 * @author Eros Candelaresi <eros@candelaresi.de>
 * @since 17.10.13 21:12
 *
 *        Collector that handle a single IMAP session.
 *
 * @param connectionInfo Hostname, port, username and password for the IMAP server
 */
class ImapCollector(connectionInfo: ImapConnectionInfo) extends Actor with ActorLogging {

  sealed trait State

  case object Initialized extends State

  case object Connecting extends State

  case object Idle extends State

  case object Busy extends State

  case object Disconnecting extends State

  case object Interrupted extends State

  case object Exiting extends State

  sealed trait Data

  case object Uninitialized extends Data

  case class ConnectionData(connectionInfo: ImapConnectionInfo, connectionContext: ImapConnectionContext)

  def receive: Actor.Receive = {
    case _ =>
  }

}
