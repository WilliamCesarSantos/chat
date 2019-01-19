# Chat

## Sumário

<!-- TOC depthFrom:1 depthTo:2 orderedList:false withLinks:false anchorMode:gitlab.com -->
- 1 - Definição do projeto
- 2 - Tecnologias
- 3 - Execução
 - 3.1 - Servidor
 - 3.2 - Cliente
<!-- /TOC -->

# 1 - Definição do projeto
Aplicação desenvolvida como exemplo da utilização de socket com os aulos do 4º semestre.
App permitir conversa em tempo real com os demais clientes registrados no servidor. Sendo necessário conexão com a internet e conhecimento do endereço do servidor para execução.

# 2 - Tecnologia
Aplicativo construído sobre os conceitos de Socket com a intenção de demonstrar o uso e funcionamento deste. A ideia do aplicativo é para que sirva de exemplo de como utilizar Socket.
Para a construção do aplicativo foram utilizados a linguagem Java 8 sem nenhum framework adicional. Neste aplicativo também é utilizado um pool de thread para atender aos clientes de forma mais rápida.

# 3 - Execução
O aplicativo é divido em duas partes. A parte servidor que armazena uma lista de todos os usuários ativos no momento e a parte cliente que é utilizada para enviar mensagem aos demais clientes. A parte cliente depende do servidor estar em execução para obter a lista de clientes atualizada.

# 3.1 - Servidor
Para iniciar o servidor basta executar o jar `server.jar` dentro da pasta server. Se estiver utilizando uma IDE basta executar a classe com.santos.will.chat.server.Server.java que é a classe main do projeto.

# 3.2 - Cliente
Para iniciar o cliente basta executar o jar `cliente.jar`, porém o servidor já deve estar em execução e o arquivo cliente.properties deve ter o endereço do servidor preenchido corretamente. Caso esteja utilizando uma IDE basta executar a classe com.santos.will.chat.client.Client.java e configurar o arquivo cliente.properties dentro do resources.