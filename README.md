# Sistema Academia (ProjetoA3)

## Descrição

Este é um sistema simples em Java (Swing + JDBC) para gerenciar alunos e seus treinos. O projeto inclui:

- Modelos: `Aluno`, `Treino` (package `model`).
- DAOs JDBC: `AlunoJDBCDAO`, `TreinoJDBCDAO` (package `dao`) — atualmente configurados para MySQL.
- Interfaces gráficas Swing: `MenuPrincipal`, `MenuAlunos`, `MenuTreinos` (package `view`).
- Utilitário para popular o banco: `util.GeradorAlunosAleatorios` — gera e insere 20 alunos aleatórios.

O formato de data utilizado na UI é localizado para Brasil: `DD-MM-YYYY`.

## Requisitos

- Java JDK 11+ instalado e `java`/`javac` no PATH.
- MySQL (ou outro servidor compatível) em execução.
- Conector JDBC do MySQL (MySQL Connector/J) — coloque o JAR em `lib/`.

## Estrutura de pastas

- `src/` — código-fonte Java.
- `lib/` — dependências externas (coloque o conector JDBC aqui).
- `bin/` — saída de classes compiladas (será criada ao compilar).

## Banco de dados (MySQL)

Por padrão os DAOs usam a URL `jdbc:mysql://localhost:3306/projeto_database` com usuário `root` e senha vazia. Se quiser usar outras credenciais, edite:

- `src/dao/AlunoJDBCDAO.java`
- `src/dao/TreinoJDBCDAO.java`

SQL de exemplo para criar o banco e as tabelas necessárias:

```sql
CREATE DATABASE projeto_database CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE projeto_database;

CREATE TABLE alunos (
	id INT AUTO_INCREMENT PRIMARY KEY,
	nome VARCHAR(150) NOT NULL,
	cpf VARCHAR(20),
	data_nascimento DATE,
	telefone VARCHAR(30),
	email VARCHAR(150)
);

CREATE TABLE treinos (
	id INT AUTO_INCREMENT PRIMARY KEY,
	id_aluno INT NOT NULL,
	treino VARCHAR(100),
	descricao TEXT,
	duracao_minutos INT,
	data_inicio DATE,
	FOREIGN KEY (id_aluno) REFERENCES alunos(id) ON DELETE CASCADE
);
```

## Compilação e execução (Windows)

1. Certifique-se de ter o MySQL em execução e de ter colocado o driver JDBC (`mysql-connector-java-*.jar`) dentro de `lib/`.
2. No prompt de comando, a partir da raiz do projeto, crie a pasta `bin` (se ainda não existir) e compile tudo:

```powershell
mkdir bin
javac -d bin -cp "lib/*" src\model\*.java src\dao\*.java src\view\*.java src\util\*.java
```

3. Rodar a interface gráfica (Menu Principal):

```powershell
java -cp "bin;lib/*" view.MenuPrincipal
```

4. Rodar o gerador de alunos (insere 20 alunos no banco):

```powershell
java -cp "bin;lib/*" util.GeradorAlunosAleatorios
```

## Notas importantes

- Se alterar o usuário/senha/URL do banco, atualize as constantes `URL`, `USER`, `PASSWORD` em `src/dao/AlunoJDBCDAO.java` e `src/dao/TreinoJDBCDAO.java`.
- O utilitário `GeradorAlunosAleatorios` usa o `AlunoJDBCDAO.adicionar()` para inserir registros — garanta que o banco e as tabelas existam antes de executar.
- As telas já recarregam automaticamente a lista de alunos/treinos após operações CRUD; os botões "Atualizar Lista" foram removidos das telas para evitar redundância.

## Problemas comuns

- Erro de conexão: verifique se o MySQL está rodando e se o conector JDBC está em `lib/`.
- Erro de permissão ao inserir: verifique usuário/senha e privilégios do usuário no banco.

## Próximos passos sugeridos

- Trocar o armazenamento para SQLite (arquivo único) para facilitar distribuição e testes locais.
- Substituir campos de data por `JFormattedTextField` ou um date picker para melhorar a entrada do usuário.

---
Arquivo de referência: `src/view/MenuAlunos.java`, `src/view/MenuTreinos.java`, `src/util/GeradorAlunosAleatorios.java`
