package grails.plugin.core.auth

class User implements Serializable {

	String username
	String passwordHash
	
	String name
	String email
	byte[] picture
	String pictureType
	String nonVerifiedEmail
	String emailHash
	String timeZone
	String language
	Date expiration
	boolean enabled = false
	
	boolean restricted = false
	Date dateCreated = new Date()
	Date lastUpdated = new Date()

	static belongsTo = [Role]

	//static hasOne = [profile: Profile]

	static hasMany = [
		roles: Role
	]

	static mapping = {
		sort username: 'desc' 
	}

	static constraints = {
		//profile (nullable: false)
		username (nullable: false, blank: false, unique: true, minSize: 4, maxSize: 255)
		passwordHash (nullable: false, blank: false)
		name(nullable: true, blank: false)
		email(nullable:true, blank:false, email: true, unique: true)
		picture(nullable:true, maxSize: 16384)
		pictureType(nullable: true)
		nonVerifiedEmail(nullable:true, blank:false, email: true)
		emailHash(nullable: true, blank:true)
		timeZone(nullable: true)
		language(nullable: true)
		roles (nullable: false)
		enabled (nullable: false)
		restricted (nullable: false)
		expiration (nullable: true)
	}

	static transients = ['pass', 'passConfirm']
	String pass
	String passConfirm

	String toString(){
		username
	}

}