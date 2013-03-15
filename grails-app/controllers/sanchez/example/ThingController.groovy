package sanchez.example

import org.springframework.dao.DataIntegrityViolationException

class ThingController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list() {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [thingInstanceList: Thing.list(params), thingInstanceTotal: Thing.count()]
    }

    def create() {
        [thingInstance: new Thing(params)]
    }

    def save() {
        def thingInstance = new Thing(params)
        if (!thingInstance.save(flush: true)) {
            render(view: "create", model: [thingInstance: thingInstance])
            return
        }

		flash.message = message(code: 'default.created.message', args: [message(code: 'thing.label', default: 'Thing'), thingInstance.id])
        redirect(action: "show", id: thingInstance.id)
    }

    def show() {
        def thingInstance = Thing.get(params.id)
        if (!thingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'thing.label', default: 'Thing'), params.id])
            redirect(action: "list")
            return
        }

        [thingInstance: thingInstance]
    }

    def edit() {
        def thingInstance = Thing.get(params.id)
        if (!thingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'thing.label', default: 'Thing'), params.id])
            redirect(action: "list")
            return
        }

        [thingInstance: thingInstance]
    }

    def update() {
        def thingInstance = Thing.get(params.id)
        if (!thingInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'thing.label', default: 'Thing'), params.id])
            redirect(action: "list")
            return
        }

        if (params.version) {
            def version = params.version.toLong()
            if (thingInstance.version > version) {
                thingInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'thing.label', default: 'Thing')] as Object[],
                          "Another user has updated this Thing while you were editing")
                render(view: "edit", model: [thingInstance: thingInstance])
                return
            }
        }

        thingInstance.properties = params

        if (!thingInstance.save(flush: true)) {
            render(view: "edit", model: [thingInstance: thingInstance])
            return
        }

		flash.message = message(code: 'default.updated.message', args: [message(code: 'thing.label', default: 'Thing'), thingInstance.id])
        redirect(action: "show", id: thingInstance.id)
    }

    def delete() {
        def thingInstance = Thing.get(params.id)
        if (!thingInstance) {
			flash.message = message(code: 'default.not.found.message', args: [message(code: 'thing.label', default: 'Thing'), params.id])
            redirect(action: "list")
            return
        }

        try {
            thingInstance.delete(flush: true)
			flash.message = message(code: 'default.deleted.message', args: [message(code: 'thing.label', default: 'Thing'), params.id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
			flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'thing.label', default: 'Thing'), params.id])
            redirect(action: "show", id: params.id)
        }
    }
}
