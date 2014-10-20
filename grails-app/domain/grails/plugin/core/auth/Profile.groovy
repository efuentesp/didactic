package grails.plugin.core.auth

import org.apache.shiro.crypto.hash.Md5Hash

class Profile implements Serializable {

	String firstName
	String lastName
	String email
	byte[] picture
	String pictureType
	String nonVerifiedEmail
	String emailHash
	String timeZone
	String language

	Date dateCreated = new Date()
	Date lastUpdated = new Date()

	def beforeInsert = { hashEmail() }

	def beforeUpdate = { hashEmail() }

	void hashEmail() {
		// Do MD5 hash of email for Gravatar
		if(email) {
			emailHash = new Md5Hash(email).toHex()
		}
	}

	static belongsTo = [owner: User]

	static constraints = {
		firstName(nullable: true, blank: false)
		lastName(nullable: true, blank: false)
		email(nullable:true, blank:false, email: true, unique: true)
		picture(nullable:true, maxSize: 16384)
		pictureType(nullable: true)
		nonVerifiedEmail(nullable:true, blank:false, email: true)
		emailHash(nullable: true, blank:true)
		timeZone(nullable: true)
		language(nullable: true)
	}

	String toString() {
		def title
		if (firstName)
			title = firstName
		if (lastName)
			title = title + ' ' + lastName
		return title
	}

}