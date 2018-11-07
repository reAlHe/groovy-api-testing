package annotation

import groovy.transform.*

@ToString(includeNames = true, includePackage = false)
@AnnotationCollector([
        TupleConstructor,
        AutoClone])
@EqualsAndHashCode(useCanEqual = false)
@interface Model {

}