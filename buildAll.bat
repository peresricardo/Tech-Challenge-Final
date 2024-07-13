@echo off
cls
echo ### Compilando srvAutenticacao ###
cd srvAutenticacao
rmdir /s /q target
call mvn clean install -U
pause
echo ### Gerando imagem Docker ###
call docker image build -t srv-autenticacao .
pause
echo ### Fim Compilacao srvAutenticacao ###
cd ..

cls
echo ### Compilando srvCliente ###
cd srvCliente
rmdir /s /q target
call mvn clean install -U
pause
echo ### Gerando imagem Docker ###
call docker image build -t srv-cliente .
pause
echo ### Fim Compilacao srvCliente ###
cd ..

cls
echo ### Compilando srvDiscovery ###
cd srvDiscovery
rmdir /s /q target
call mvn clean install -U
pause
echo ### Gerando imagem Docker ###
call docker image build -t srv-discovery .
pause
echo ### Fim Compilacao srvDiscovery ###
cd ..

cls
echo ### Compilando srvGateway ###
cd srvGateway
rmdir /s /q target
call mvn clean install -U
pause
echo ### Gerando imagem Docker ###
call docker image build -t srv-gateway .
pause
echo ### Fim Compilacao srvGateway ###
cd ..

cls
echo ### Compilando srvItem ###
cd srvItem
rmdir /s /q target
call mvn clean install -U
pause
echo ### Gerando imagem Docker ###
call docker image build -t srv-item .
pause
echo ### Fim Compilacao srvItem ###
cd ..

cls
echo ### Compilando srvPagamento ###
cd srvPagamento
rmdir /s /q target
call mvn clean install -U
pause
echo ### Gerando imagem Docker ###
call docker image build -t srv-pagamento .
pause
echo ### Fim Compilacao srvPagamento ###
cd ..

cls
echo ### Compilando srvCarrinho ###
cd carrinhoDeCompras
rmdir /s /q target
call mvn clean install -U
pause
echo ### Gerando imagem Docker ###
call docker image build -t srv-carrinho .
pause
echo ### Fim Compilacao srvCarrinho ###
cd ..
