# 🎲 RPG de Texto em Java (POO)

Uma versão limpa e interativa do README para facilitar leitura e uso.

## 📌 Descrição
Jogo de RPG em texto desenvolvido em Java para aplicar conceitos de Programação Orientada a Objetos. Mecânicas principais: combates por rolagem de dados, uso de itens em inventário e navegação em uma história simples.

## 🚀 Como executar (via Maven — recomendado)
Requisitos:
- JDK 11+ instalado e JAVA_HOME configurado.
- Apache Maven instalado (mvn disponível no PATH).
- `pom.xml` presente na raiz do projeto.

Passos (terminal integrado do VS Code / PowerShell / bash):
1. Abra o projeto no VS Code:
   - File > Open Folder > selecione a pasta do projeto (ex.: ProjetoRPG).
   - O terminal integrado já abre na raiz do projeto por padrão. Se não abrir, navegue até a raiz do projeto:
     - Windows (exemplo genérico): cd "C:\caminho\para\ProjetoRPG"
     - macOS / Linux (exemplo genérico): cd "/home/usuario/ProjetoRPG"
     - Ou use variáveis de ambiente: cd "%USERPROFILE%\ProjetoRPG"
2. Verifique instalações:
   - java -version
   - mvn -v
3. Compilar e empacotar:
   - mvn clean package
4. Executar via exec-plugin (substitua pelo nome do pacote/classe principal):
   - mvn -Dexec.mainClass="com.seupacote.Jogo" exec:java
5. Ou executar o JAR gerado (se o MANIFEST.MF contiver Main-Class):
   - java -jar target/seu-artifact-1.0-SNAPSHOT.jar

Observações rápidas:
- Se o terminal não estiver na raiz do projeto, abra o painel Explorer no VS Code, clique com o botão direito na pasta do projeto e escolha "Open in Integrated Terminal".
- Substitua "com.seupacote.Jogo" e nome do artifact pelo pacote/classe e artifact do seu projeto.

Se precisar do plugin exec no pom.xml, adicione:
```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.codehaus.mojo</groupId>
      <artifactId>exec-maven-plugin</artifactId>
      <version>3.1.0</version>
    </plugin>
  </plugins>
</build>
```

## ▶️ Como jogar (comandos comuns)
- Explorar — procurar locais, inimigos e itens.
- Inventário — listar itens ordenados.
- Usar [id/nome] — aplicar efeito do item (reduz quantidade).
- Fugir — rolar dado para tentativa de fuga.
- Status — ver atributos do personagem.
- Sair — encerrar o jogo.

## 🧩 Estrutura principal (resumo)
- Personagem (abstrata)
  - Atributos: nome, pontosVida, ataque, defesa, nivel, Inventario inventario
  - Subclasses: Guerreiro, Mago, Arqueiro
  - Inimigo deriva de Personagem
  - Requer: construtor padrão e construtor de cópia
- Item
  - Atributos: nome, descricao, efeito, quantidade
  - Usar decrementa quantidade
  - equals() para detectar igualdade (somar quantidade)
  - compareTo() para ordenação
- Inventario
  - Adicionar item (incrementa quantidade se existir)
  - Remover item (reduz quantidade)
  - Listar itens ordenados
  - Implementar clone() / construtor de cópia para deep copy

## ⚔️ Sistema de combate
Método: batalhar(Inimigo inimigo)  
Mecânica por turno:
1. Jogador e inimigo rolam dados.
2. Soma do dado + atributo ataque.
3. Ataque efetivo se (ataque + dado) > defesa do adversário.
4. Combate encerra quando pontosVida <= 0 de qualquer lado.

## ✅ Requisitos obrigatórios (a implementar)
- toString(), equals(), hashCode()
- clone() e/ou construtor de cópia
- compareTo() onde aplicável

## 🧪 Testes recomendados
- Cenários de combate (vários níveis/armas/defesas).
- Uso de itens e decremento correto de quantidade.
- Clonagem de inventário sem referências compartilhadas.
- Ordenação e mesclagem de itens iguais (equals).

## 💻 Extensões VS Code recomendadas para uso com Maven
- Extension Pack for Java (Microsoft)  
- Maven for Java (Microsoft)  
- Language Support for Java(TM) by Red Hat  
- Debugger for Java  
- Java Test Runner

## 💡 Dicas rápidas
- Priorize código limpo e nomes claros.
- Documente métodos públicos com javadoc.
- Escreva testes unitários para batalhas e inventário.

--- 
Pequena checklist antes da entrega:
- [ ] pom.xml presente e configurado
- [ ] Construtores e clones implementados
- [ ] toString/equals/hashCode testados
- [ ] Inventário com deep copy
- [ ] Cenários de combate testados