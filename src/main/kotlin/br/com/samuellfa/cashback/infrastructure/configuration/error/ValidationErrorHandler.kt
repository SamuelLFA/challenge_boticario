package br.com.samuellfa.cashback.infrastructure.configuration.error

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.IllegalArgumentException
import javax.servlet.http.HttpServletRequest
import javax.validation.ConstraintViolationException

@RestControllerAdvice
class ValidationErrorHandler @Autowired constructor(val messageSource: MessageSource) {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleException(exception: MethodArgumentNotValidException): List<FormErrorDTO> {
        val fieldErrors = exception.bindingResult.fieldErrors
        return fieldErrors
            .map { fieldError: FieldError ->
                val message = messageSource.getMessage(fieldError, LocaleContextHolder.getLocale())
                FormErrorDTO(fieldError.field, message)
            }
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleException(exception: ConstraintViolationException): List<FormErrorDTO> {
        val errors = exception.constraintViolations
        return errors
            .map { error ->
                FormErrorDTO(error.propertyPath.last().name, error.message)
            }
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleException(exception: HttpMessageNotReadableException, request: HttpServletRequest) : ErrorViewDTO {

        return ErrorViewDTO(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = exception.localizedMessage,
            path = request.servletPath
        )
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleException(exception: IllegalArgumentException, request: HttpServletRequest) : ErrorViewDTO {

        return ErrorViewDTO(
            status = HttpStatus.BAD_REQUEST.value(),
            error = HttpStatus.BAD_REQUEST.name,
            message = exception.localizedMessage,
            path = request.servletPath
        )
    }
}
