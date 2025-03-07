-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema Gestion_Formation
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema Gestion_Formation
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `Gestion_Formation` DEFAULT CHARACTER SET utf8 ;
USE `Gestion_Formation` ;

-- -----------------------------------------------------
-- Table `Gestion_Formation`.`utilisateurs`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Gestion_Formation`.`utilisateurs` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `nom` VARCHAR(45) NOT NULL,
  `email` VARCHAR(45) NOT NULL,
  `mot_de_pass` VARCHAR(45) NOT NULL,
  `user_type` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Gestion_Formation`.`formations`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Gestion_Formation`.`formations` (
  `id_for` INT NOT NULL AUTO_INCREMENT,
  `titre` VARCHAR(45) NOT NULL,
  `description` VARCHAR(100) NULL,
  `prix` DECIMAL(10,2) NULL,
  `id_formateur` INT NOT NULL,
  PRIMARY KEY (`id_for`),
  INDEX `id_formateur_idx` (`id_formateur` ASC) VISIBLE,
  CONSTRAINT `formateur`
    FOREIGN KEY (`id_formateur`)
    REFERENCES `Gestion_Formation`.`utilisateurs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `Gestion_Formation`.`inscriptions`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `Gestion_Formation`.`inscriptions` (
  `id_Etu` INT NOT NULL,
  `id_For` INT NOT NULL,
  PRIMARY KEY (`id_Etu`, `id_For`),
  INDEX `formation_idx` (`id_For` ASC) VISIBLE,
  CONSTRAINT `formation`
    FOREIGN KEY (`id_For`)
    REFERENCES `Gestion_Formation`.`formations` (`id_for`)
    ON DELETE CASCADE
    ON UPDATE CASCADE,
  CONSTRAINT `etudiant`
    FOREIGN KEY (`id_Etu`)
    REFERENCES `Gestion_Formation`.`utilisateurs` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
