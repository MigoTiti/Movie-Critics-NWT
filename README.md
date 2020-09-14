# Movie-Critics-NWT

Projeto para o teste prático da your/dev, usando a API de movie reviews do New York Times 

## Arquitetura

O projeto foi desenvolvido em Kotlin, utilizando MVVM junto com repository pattern para controle de data sources. Além disso, foi utilizado Koin para gerenciar a injeção de dependências, assim permitindo o desenvolvimento do app sem a necessidade de uma API implementada. Para a exibição das listas paginadas, foi utilizada a biblioteca Paging 3 do Android Jetpack, que permite o uso de Webservices junto com banco de dados local para permitir uma experiência de usuário offline-first. Para o banco de dados, foi utilizado Room, componente do Android Jetpack. Na camada de view, foi utilizado data binding para facilitar a comunicação com os ViewModels, além de ConstraintLayout junto de MotionLayout para animações. Para controlar o mock de dados, foi feita a utilização de flavors. O projeto também foi internacionalizado onde possível.

## Testes

Para testar com dados mockados ao invés dos dados da API, basta selecionar o flavor "mocked". E para testar com os dados da API, basta selecionar o flavor "unmocked".
