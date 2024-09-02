# Loja GreenLine

## 🚀 Demanda

Desenvolver uma API RESTFULL (back-end) utilizando **SpringBoot e MySQL** para Loja de eletronicos, e posteriormente desenvolver front-end com **TypeScript e framework(Angular)**.

### Entidades:

- `Categoria:` idCategoria, descricao.<br>
- `Categoria_Produto:` idCategoria, idProduto .<br>
- `Produto:` idProduto, idCategoria, idFornecedor, nome, valor, descricao.<br>
- `Fornecedor:`idFornecedor, nomeFornecedor, cnpj, emailFornecedor .<br>
- `Iten_Venda:` idPedido, idProduto, idUsuario, idVenda, quanrProd.<br>
- `Venda:` idVenda, descricao, valor .<br>
- `Usuario:` idUsuario, idCliente, historico.<br>
- `Clinte:` idCliente, nome, telefone, endereco, email, senha.<br>

### Relacionamentos:

- Uma Categoria pode ter vários Produtos, e um Produto pode estar vinculado a várias Categoria. (ManyToMany)
- Um Fornecedor pode estar vinculado a vários Produtos, mas um Produto só pode estar associado 
a um Fornecedor. (ManyToOne)
- Uma Venda pode ter vários Produtos, e um Produto uma Venda. (ManyToOne)
- Uma Venda pode ter um Usuario, mas uma Usuario pode estar associada a várias Vendas. (OneToMany)
- Um Usuario pode ter um Cliente, e um Cliente pode ter apenas um Usuario. (OneToOne)

### Regras de negócio e validações:

- As entidades principas são Venda e Produto. O sistema deve permitir salvar a Produto com a Categoria e com a Venda
, o Produto e a Categoria em uma única requisição (cascade), e o Produto e a Venda em uma única requisição (cascade).
- Todos os atributos das entidades são obrigatórios, incluindo as validation e relacionamentos (Many e One).
- Além de métodos básicos de CRUD (findAll, findById, delete, save e update), implementar
ao menos 3 filtros (findBy...), sendo 01 com JPQL e 02 com métodos automáticos para cada
repository de cada uma das 4 entidades.
- Todas as entidades devem possuir endpoints, então, haverá controller, service, repository
para cada entidade e seus respectivos mappings.
- Aplicar testes automatizados com carater geral e unitário, nos package, com o valor minimo
necessário que deve ser obtido no converge é de 50%.

## Requesitos que serão avaliados

- `Padão organizacionais do Spring Boot:`
- `Persistência com SpringDataJPA:`
- `Estrututa de requisições HTTP:`
- `Funcionamento das requisições pelo POSTMAN:`
- `Solução do problema (interpretação):`
- `Testes automatizados com 50% de converge:`

## Dependências

- SPRING WEB
- SPRING DATA JPA
- SPRING VALIDATION
- MYSQL CONECTOR
- LOMBOK
- STARTER TEST
- JACACO TEST

## Estrutura do Projeto
```
📁 Loja Green Line
└── 📁src/main/java
    ├──📁app.auditing
    │   ├──📁ApplicationAuditAware
    │   ├──📁Audit
    ├──📁app.auth
    │   ├──📁Autenticator
    │   ├──📁LoginController
    │   ├──📁LoginRepository
    │   ├──📁LoginService
    │   ├──📁Usuario
    │   ├──📁UsuarioController
    │   ├──📁UsuarioRepository
    │   ├──📁UsuarioService
    ├──📁app.controller
    │   ├──📁 categoriaController
    │   ├──📁 produtoController
    │   ├──📁fornecedorController
    │   ├──📁 vendaController
    │   ├──📁 usuarioController
    │   ├──📁 clienteController
    ├──📁 app.entity
    │   ├──📁 Cantegoria
    │   ├──📁 Produto
    │   ├──📁 Fornecedor
    │   ├──📁 Venda
    │   ├──📁 Usuario
    │   ├──📁 Cliente
    │──📁 app.repository
    │   ├──📁 categoriaRepository
    │   ├──📁 produtoRepository
    │   ├──📁 fornecedorRepository
    │   ├──📁 vendaRepository
    │   ├──📁 usuarioRepository
    │   ├──📁 clienteRepository
    ├──📁 app.service
    │    ├──📁 categoria.Service
    │    ├──📁 produto.Service
    │    ├──📁 fornecedor.Service
    │    ├──📁 venda.Service
    │    ├──📁 usuario.Service
    │    ├──📁 cliente.Service
├── 📁 src/main/resources
    │    ├──📁 application.properties
├──📁 src/test/java
   ├──📁 app.controller
    │   ├──📁 CategoriaControllerTest
    │   ├──📁 ProdutoControllerTest
    │   ├──📁 FornecedorControllerTest
    │   ├──📁 VendaControllerTest
    │   ├──📁 UsuarioControllerTest
    │   ├──📁 ClienteControllerTest
    ├──📁 app.service
    │    ├──📁 CategoriaServiceTest
    │    ├──📁 ProdutoServiceTest
    │    ├──📁 FornecedorServiceTest
    │    ├──📁 VendaServiceTest
    │    ├──📁 UsuarioServiceTest
    │    ├──📁 ClienteServiceTest
├──📁 JRE System Library
├──📁 Maven  Dependencies
├──📁 src
├──📁 target
   ├──📁 POM.XML
   ├──📁 README.md 
   ```

## 💻 Trello
  [TRELLO](https://trello.com/invite/b/aKBOSCax/ATTI60239fda27f435cec23cab19bf1ab1c2180DF94F/loja-green-line)

## 🤝 Colaboradores

Agradecemos às seguintes pessoas que contribuíram para este projeto:

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/CarlosFeliponi">
        <img src="https://avatars.githubusercontent.com/u/107933029?v=4" width="100px;" alt="Carlos Feliponi Github"/><br>
        <sub>
          <b>Carlos Feliponi</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Exxodius">
        <img src="https://avatars.githubusercontent.com/u/119608284?v=4" width="100px;" alt="Gabriel Pletsch Github"/><br>
        <sub>
          <b>Gabriel Pletsch</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/PedroHenriqueAbreuF">
        <img src="https://avatars.githubusercontent.com/u/101679821?v=4" width="100px;" alt="Carlos Antunes Github"/><br>
        <sub>
          <b>Pedro Henrique de Abreu</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/lucasSchneider1999">
        <img src="https://avatars.githubusercontent.com/u/129910935?v=4" width="100px" alt="Lucas Shneider"/><br>
        <sub>
          <b>Lucas Shneider</b>
        </sub>
      </a>
    </td>
     <td align="center">
      <a href="https://github.com/gustavoronan">
        <img src="https://avatars.githubusercontent.com/u/129910842?v=4" width="100px" alt="Gustavo Ronan"/><br>
        <sub>
          <b>Gustavo Ronan</b>
        </sub>
      </a>
    </td>
  </tr>
</table> 
