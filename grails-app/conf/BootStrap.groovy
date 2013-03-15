import sanchez.example.*

class BootStrap {
	def init = { servletContext ->
		['time', 'person', 'year', 'way', 'day', 'thing', 'man', 'world', 'life', 'hand', 'part', 'child', 'eye', 'woman', 'place', 'work', 'week', 'case', 'point', 'government', 'company', 'number', 'group', 'problem', 'fact'].each {
			new Thing(name:it).save()
		}
	}
	def destroy = {
	}
}
