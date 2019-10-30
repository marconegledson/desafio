## O DESAFIO! 

#### Introdução

Projeto com exemplos de utilização de **rest** utilizando spring boot



#### Exemplos
Este projeto representa um exemplo simples
Todas as classes Rest extendem AbstractRestController contendo os métodos principais de um CRUD REST. A classe de rest controller invoca os métodos da classe de serviço com o objetivo de ser **simples, bonito e possuir boa manutenabilidade**.
For example:

rest controller:

```java
public abstract class AbstractRestController<T extends AbstractPersistable<PK>, PK extends Serializable>	
	@GetMapping("/{id}")
	public T getById(@PathVariable PK id) {
		log.debug(">> getById {}", id);
		T entity = getService().findById(id);
		log.debug("<< getById {} {}", id, entity);
		return entity;
	}
...
}

@RestController
@RequestMapping("/api/usuario")
public class UsuarioRestController extends AbstractRestController<Usuario, Long>{
	...
}


```

camada de serviço :

```java

@Slf4j
public abstract class AbstractService<T extends AbstractPersistable<PK>, PK extends Serializable>	
	public T findById(PK id) {
		log.debug(">> findById [id={}] ", id);
		Optional<T> entity = getRepository().findById(id);
		log.debug("<< findById [id={}, entity={}] ", id, entity);
		return entity.orElseThrow(() -> new EntityNotFoundException());
	}
...
}

@Slf4j
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioService extends AbstractService<Usuario, Long>{
...
}

```

A organização do projeto está dividida.
A entidade Usuario possui:
* A RestController : a classe UsuarioRestController usada para definir e criar a api rest.
* A UsuarioService : Classe de negocio utilizada para realizar o processamento, intermediando a chamada a base de dados
* A UsuarioRepository : A interface  que  extende JpaRepository, para possuir as principais ações de CRUD e realizadar as pesquisa na base de dados
* O Usuario : A entidade que representa a tabela no banco de dados

#### Exemplos de utilizacao

O propósito do projeto é ser um rest de leitura que será consumido por qualquer cliente. Exemplos das chamadas:


**[GET] http://localhost:8080/api/usuario/1**  Traz o usuario por um determinado id  
**[GET] http://localhost:8080/api/usuario/**  Traz todos os usuarios  
**[PUT] http://localhost:8080/api/usuario/1**  Atualiza o usuario de um determinado id com as informações de outro usuario  
**[POST] http://localhost:8080/api/usuario/**  Insere um  novo usuario  
**[DELETE] http://localhost:8080/api/usuario/1**  Remove o usuario por um determinado id  

#### Instalacao and Configuracao

Para a instação o Projeto não requer muito.  
1. A Maven project >= 3.2 
2. Clonar o projeto e executar utilizando o comando **mvn spring-boot:run**.
3. Abrir o browser e realizar as chamadas da api contidas nos **Exemplos de utilizacao**

Para executar os testes.
Para a instação o Projeto não requer muito.  
1. A Maven project >= 3.2 
2. Clonar o projeto e executar utilizando o comando **mvn test**.

**Sobre a configuração**: o arquivo application.properties file tem linhas comentadas sobre a configuração do log.
Para melhor performance, os logs estao comentados e podem ser ativados.

