# Agenda de Clínica

Sistema de Agenda para Clínicas de Saúde.

## Tecnologias

| Camada | Tecnologia |
|--------|-----------|
| Backend | Java 17 + Spring Boot 3.2 |
| Frontend | React 18 + React Router |
| Banco de Dados | PostgreSQL 15 |
| Build Backend | Maven |
| Build Frontend | Node.js 20 + npm |
| Versionamento | Git + GitHub |
| CI/CD | GitHub Actions |
| Containers | Docker + Docker Compose |
| Produção | AWS (ECS + RDS + ECR + ALB) |

## Estrutura do Projeto

```
clinica/
├── backend/           # API REST (Java/Spring Boot)
│   ├── pom.xml
│   ├── Dockerfile
│   └── src/
├── frontend/          # UI (React)
│   ├── package.json
│   ├── Dockerfile
│   └── src/
├── docker-compose.yml
└── shell.nix          # Nix environment
```

## Tutorial para Usuários Finais

Siga os passos abaixo para executar e utilizar a aplicação na sua máquina. Não é necessário instalar nenhuma ferramenta de desenvolvimento, apenas o **Docker** e o **Docker Compose**.

### 1. Iniciar a Aplicação

Abra o terminal, navegue até a pasta `clinica` e execute o comando abaixo. Ele fará o download, a compilação e a inicialização de todo o sistema em segundo plano:

```bash
cd clinica
docker-compose up -d --build
```
> O parâmetro `-d` roda o sistema em segundo plano e `--build` garante que a interface tenha as últimas atualizações.

### 2. Acessar o Sistema

Quando o terminal finalizar o processo e liberar para novos comandos, a aplicação estará no ar. Abra o seu navegador de internet e acesse:

👉 **[http://localhost:3000](http://localhost:3000)**

Na interface, você poderá:
*   Adicionar e excluir **Profissionais de Saúde**.
*   Registrar novos **Atendimentos**, selecionando a data, horário e o profissional responsável no menu.
*   Cadastrar **Exames de Laboratório**, os vinculando diretamente aos Atendimentos já registrados.

*Atenção: Os dados são atualizados automaticamente na tela em todas as tabelas e opções quando você adiciona um novo registro.*

### 3. Encerrar a Aplicação

Quando você não for mais usar o sistema, é importante desligá-lo para liberar a memória do seu computador. No mesmo terminal e pasta onde você iniciou o sistema, execute:

```bash
docker-compose down
```
Isso desligará os servidores e o banco de dados com segurança.

### Como Executar os Testes (Local)

Se você utiliza o **Nix**, você pode entrar no ambiente usando o comando abaixo para garantir as versões corretas (Java 17 e Node 20):
```bash
cd clinica
export NIXPKGS_ALLOW_INSECURE=1
nix-shell
```

**Para o Backend (Java):**
```bash
cd backend
mvn test
```

**Para o Frontend (React):**
```bash
cd frontend
npm ci
CI=true npm test
```

## Para Desligar a Aplicação

Quando terminar, basta rodar o comando abaixo para desligar e remover os containers:
```bash
docker-compose down
```
