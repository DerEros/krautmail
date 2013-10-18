package de.erna.krautmail.collectors.imap

import java.net.InetSocketAddress

/**
 * @author Eros Candelaresi <eros@candelaresi.de>
 * @since 17.10.13 21:15
 *
 *        Configuration object to carry IMAP server connection information.
 */
case class ImapConnectionInfo(endpoint: InetSocketAddress, username: String, password: String)
