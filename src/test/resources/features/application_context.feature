# language: pt

@phase1 @bdd
Funcionalidade: Sanidade da aplicação

  Cenário: Contexto Spring inicializa
    Quando a aplicação de testes for iniciada
    Então o contexto Spring deve subir sem erros
