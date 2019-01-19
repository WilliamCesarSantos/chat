# Chat

## Sum�rio

<!-- TOC depthFrom:1 depthTo:2 orderedList:false withLinks:false anchorMode:gitlab.com -->
- 1 - Defini��o do projeto
- 2 - Tecnologias
- 3 - Execu��o
 - 3.1 - Servidor
 - 3.2 - Cliente
<!-- /TOC -->

# 1 - Defini��o do projeto
Aplica��o desenvolvida como exemplo da utiliza��o de socket com os aulos do 4� semestre.
App permitir conversa em tempo real com os demais clientes registrados no servidor. Sendo necess�rio conex�o com a internet e conhecimento do endere�o do servidor para execu��o.

# 2 - Tecnologia
Aplicativo constru�do sobre os conceitos de Socket com a inten��o de demonstrar o uso e funcionamento deste. A ideia do aplicativo � para que sirva de exemplo de como utilizar Socket.
Para a constru��o do aplicativo foram utilizados a linguagem Java 8 sem nenhum framework adicional. Neste aplicativo tamb�m � utilizado um pool de thread para atender aos clientes de forma mais r�pida.

# 3 - Execu��o
O aplicativo � divido em duas partes. A parte servidor que armazena uma lista de todos os usu�rios ativos no momento e a parte cliente que � utilizada para enviar mensagem aos demais clientes. A parte cliente depende do servidor estar em execu��o para obter a lista de clientes atualizada.

# 3.1 - Servidor
Para iniciar o servidor basta executar o jar `server.jar` dentro da pasta server. Se estiver utilizando uma IDE basta executar a classe com.santos.will.chat.server.Server.java que � a classe main do projeto.

# 3.2 - Cliente
Para iniciar o cliente basta executar o jar `cliente.jar`, por�m o servidor j� deve estar em execu��o e o arquivo cliente.properties deve ter o endere�o do servidor preenchido corretamente. Caso esteja utilizando uma IDE basta executar a classe com.santos.will.chat.client.Client.java e configurar o arquivo cliente.properties dentro do resources.