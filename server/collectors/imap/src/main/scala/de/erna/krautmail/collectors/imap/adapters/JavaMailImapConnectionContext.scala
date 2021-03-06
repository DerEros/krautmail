package de.erna.krautmail.collectors.imap.adapters

import de.erna.krautmail.collectors.imap.ImapConnectionContext
import javax.mail.{Store, Session}

/**
 * User: Eros Candelaresi <eros@candelaresi.de>
 * Project: server
 * Date: 18.10.2013
 * Time: 10:16
 *
 * Storage class to keep connection information of the JavaMail IMAP adapter between calls.
 */
case class JavaMailImapConnectionContext(session: Option[Session] = None,
                                         store: Option[Store] = None)
  extends ImapConnectionContext {
}
