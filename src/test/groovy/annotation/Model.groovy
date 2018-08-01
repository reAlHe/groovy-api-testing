package annotation

import groovy.transform.*

/**
 * @author Alexander Henze, MaibornWolff GmbH
 */
@ToString(includeNames = true, includePackage = false)
@AnnotationCollector([
        TupleConstructor,
        AutoClone])
@EqualsAndHashCode(useCanEqual = false)
@interface Model {

}