ChatRMI With JUnit Test and JMock
=================================

Este projeto tem como objetivo servir de base para validação e construção do MakeATest para RMI (MakeATest-RMi Projects) que fará testes unitários com servidores RMI (Remote Method Invocation).Este projeto foi dividido em 4 porjetos (Chat-Client, Chat-Common, Chat-Server e Chat-Test).

Chat-Client
-----------

Possui as implementações necessárias para o Cliente rodar a aplicação do chat com RMI em Swing, implementando as interfaces remotas IMessage e INotify.

Chat-Common
-----------

Possui as remotas IMessage e INotify.


Chat-Server
-----------

Possui a implementação da interface remota IMessage além de conter uma Collection com instâncias de INotify enviadas por cada cliente, onde a finalidade principal é a cada método de IMessage invocado por um cliente, atualizar todos os demais clientes com a ação (escrever mensagem no JTextArea do mesmo).

Chat-Test
-----------

Possui a implementação dos testes unitários, onde um mock das interfaces involvidas é criado, um stub deste é gerado e colocado (bind) no RMIRegistry, tudo programaticamente, para estudos das futuras anotações no MakeATest.