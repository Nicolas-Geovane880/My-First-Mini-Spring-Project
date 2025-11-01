### Notable Changes

The GlobalHandlerException was changed to support HttpsInternalsExceptions, like malformed JSON body.

GlobalHandlerException now extends ResponseEntityExceptionHandler, and by now it can handle with more exceptions.

