CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

-- Estrutura da tabela `books`
CREATE TABLE `books` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `titulo` VARCHAR(255) NOT NULL,
  `autor` VARCHAR(100) NOT NULL,
  `isbn` VARCHAR(20) NOT NULL,
  `data_publicacao` DATE DEFAULT NULL,
  `status` ENUM('DISPONIVEL','EMPRESTADO') NOT NULL DEFAULT 'DISPONIVEL',
  PRIMARY KEY (`id`),
  UNIQUE KEY `isbn` (`isbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Estrutura da tabela `customers`
CREATE TABLE `customers` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `nome` VARCHAR(100) NOT NULL,
  `sobrenome` VARCHAR(100) NOT NULL,
  `endereco` VARCHAR(255) DEFAULT NULL,
  `cidade` VARCHAR(100) DEFAULT NULL,
  `estado` VARCHAR(100) DEFAULT NULL,
  `pais` VARCHAR(100) DEFAULT NULL,
  `data_nascimento` DATE DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Estrutura da tabela `loans`
CREATE TABLE `loans` (
  `id` BIGINT NOT NULL AUTO_INCREMENT,
  `customer_id` BIGINT NOT NULL,
  `data_emprestimo` DATE NOT NULL,
  `data_devolucao` DATE DEFAULT NULL,
  `status` ENUM('EMPRESTADO', 'DEVOLVIDO') NOT NULL DEFAULT 'EMPRESTADO',
  PRIMARY KEY (`id`),
  KEY `customer_id` (`customer_id`),
  CONSTRAINT `loans_ibfk_1` FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Tabela de associação para relação Many-to-Many entre `loans` e `books`
CREATE TABLE `loan_books` (
  `loan_books_id` BIGINT NOT NULL AUTO_INCREMENT,
  `loan_id` BIGINT NOT NULL,
  `book_id` BIGINT NOT NULL,
  PRIMARY KEY (`loan_books_id`),
  KEY `loan_id` (`loan_id`),
  KEY `book_id` (`book_id`),
  CONSTRAINT `loan_books_ibfk_1` FOREIGN KEY (`loan_id`) REFERENCES `loans` (`id`) ON DELETE CASCADE,
  CONSTRAINT `loan_books_ibfk_2` FOREIGN KEY (`book_id`) REFERENCES `books` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
