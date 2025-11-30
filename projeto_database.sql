-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Tempo de geração: 01/12/2025 às 00:51
-- Versão do servidor: 10.4.32-MariaDB
-- Versão do PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Banco de dados: `projeto_database`
--

-- --------------------------------------------------------

--
-- Estrutura para tabela `alunos`
--

CREATE TABLE `alunos` (
  `id` int(11) NOT NULL,
  `nome` varchar(100) NOT NULL,
  `cpf` varchar(14) NOT NULL,
  `data_nascimento` date NOT NULL,
  `telefone` varchar(15) NOT NULL,
  `email` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `alunos`
--

INSERT INTO `alunos` (`id`, `nome`, `cpf`, `data_nascimento`, `telefone`, `email`) VALUES
(4, 'Aluno Teste JDBC', '111.222.333-44', '1999-05-20', '99999-8888', 'teste@jdbc.com'),
(5, 'Joao munaretolas', '111.111.111-00', '2004-06-06', '47 99999 9999', 'muanreto@gmail.com'),
(6, 'teste', '100.000.000-00', '2023-06-06', '47 99999 9999', 'dasdad'),
(8, 'dasdasd', 'asdsad', '2023-05-06', 'dasdasd', 'dasdasd'),
(9, '3eweqwewqeq', 'eqweqw', '2023-05-06', 'e3123123', '312321312'),
(10, 'dasdsad', '31231dasa2', '2023-05-06', '123123213', 'dasdsad'),
(11, 'Daniela Rocha Alves', '128.556.954-19', '2010-09-05', '(29) 55096-5588', 'daniela.rocha.alves65@gmail.com'),
(12, 'Eduardo Santos Costa', '933.359.162-33', '2001-07-26', '(76) 29962-2252', 'eduardo.santos.costa57@outlook.com'),
(13, 'Kaio Sousa Silva', '341.237.133-63', '2001-03-04', '(00) 55929-2245', 'kaio.sousa.silva73@mail.com'),
(14, 'Patricia Barros Martins', '115.259.223-56', '2005-01-01', '(43) 63143-9088', 'patricia.barros.martins26@hotmail.com'),
(15, 'Nathalia Ribeiro Pereira', '789.629.843-26', '2004-09-10', '(04) 76908-1605', 'nathalia.ribeiro.pereira47@yahoo.com'),
(16, 'Fernanda Oliveira Pereira', '604.323.016-04', '1996-06-16', '(03) 91194-3839', 'fernanda.oliveira.pereira42@outlook.com'),
(17, 'Samuel Teixeira Silva', '465.421.182-96', '2005-03-03', '(65) 44079-3701', 'samuel.teixeira.silva31@mail.com'),
(18, 'Daniela Rocha Ferreira', '208.273.717-47', '2003-04-04', '(86) 33505-1342', 'daniela.rocha.ferreira14@hotmail.com'),
(19, 'Otavio Dias Ferreira', '430.116.468-77', '2003-10-06', '(59) 05237-6450', 'otavio.dias.ferreira57@hotmail.com'),
(20, 'Kaio Sousa Oliveira', '600.422.768-63', '1993-11-09', '(60) 37731-4736', 'kaio.sousa.oliveira4@gmail.com'),
(21, 'Carlos Mendes Martins', '009.127.251-94', '1999-10-15', '(61) 66382-9826', 'carlos.mendes.martins1@yahoo.com'),
(22, 'Kaio Sousa Pereira', '386.898.784-75', '1996-08-06', '(91) 94614-7436', 'kaio.sousa.pereira62@yahoo.com'),
(23, 'Carlos Mendes Costa', '170.022.951-47', '1996-04-08', '(03) 20419-1051', 'carlos.mendes.costa58@mail.com'),
(24, 'Igor Pereira Souza', '582.375.123-36', '2000-06-23', '(32) 66426-7893', 'igor.pereira.souza60@mail.com'),
(25, 'Larissa Alves Costa', '529.866.832-23', '2010-02-26', '(99) 43613-1303', 'larissa.alves.costa59@outlook.com'),
(26, 'Kaio Sousa Alves', '627.163.470-44', '2002-06-18', '(55) 69958-2768', 'kaio.sousa.alves14@yahoo.com'),
(27, 'Raissa Cardoso Silva', '646.852.220-45', '1995-05-03', '(14) 88262-2363', 'raissa.cardoso.silva12@gmail.com'),
(28, 'Fernanda Oliveira Alves', '609.049.207-26', '2006-07-02', '(81) 28308-3276', 'fernanda.oliveira.alves31@mail.com'),
(29, 'Nathalia Ribeiro Oliveira', '166.153.302-50', '2009-09-16', '(32) 37953-7136', 'nathalia.ribeiro.oliveira36@yahoo.com'),
(30, 'Bruno Costa Pereira', '187.243.439-48', '1990-09-02', '(82) 57882-4224', 'bruno.costa.pereira41@hotmail.com');

-- --------------------------------------------------------

--
-- Estrutura para tabela `treinos`
--

CREATE TABLE `treinos` (
  `id` int(11) NOT NULL,
  `id_aluno` int(11) NOT NULL,
  `treino` varchar(50) NOT NULL,
  `descricao` text DEFAULT NULL,
  `duracao_minutos` int(11) NOT NULL,
  `data_inicio` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Despejando dados para a tabela `treinos`
--

INSERT INTO `treinos` (`id`, `id_aluno`, `treino`, `descricao`, `duracao_minutos`, `data_inicio`) VALUES
(3, 4, 'Treino A', 'Peito e tríceps', 60, '2025-11-30'),
(4, 11, 'Supino', 'Ele levanta peso', 20, '2003-01-01'),
(5, 4, 'fdfd', 'fdfd', 20, '2023-01-30');

--
-- Índices para tabelas despejadas
--

--
-- Índices de tabela `alunos`
--
ALTER TABLE `alunos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `cpf` (`cpf`);

--
-- Índices de tabela `treinos`
--
ALTER TABLE `treinos`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_aluno` (`id_aluno`);

--
-- AUTO_INCREMENT para tabelas despejadas
--

--
-- AUTO_INCREMENT de tabela `alunos`
--
ALTER TABLE `alunos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=31;

--
-- AUTO_INCREMENT de tabela `treinos`
--
ALTER TABLE `treinos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Restrições para tabelas despejadas
--

--
-- Restrições para tabelas `treinos`
--
ALTER TABLE `treinos`
  ADD CONSTRAINT `fk_treinos_alunos` FOREIGN KEY (`id_aluno`) REFERENCES `alunos` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
