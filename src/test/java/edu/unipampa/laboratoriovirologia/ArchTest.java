package edu.unipampa.laboratoriovirologia;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("edu.unipampa.laboratoriovirologia");

        noClasses()
            .that()
            .resideInAnyPackage("edu.unipampa.laboratoriovirologia.service..")
            .or()
            .resideInAnyPackage("edu.unipampa.laboratoriovirologia.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..edu.unipampa.laboratoriovirologia.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
