# language: pt

Funcionalidade: Modelo de limite

  Cenário: Uma janela válida mantém a soma dos contadores igual ao limite
    Dado uma janela diária com limite 10
    Quando disponível for 7, reservado 2 e consumido 1
    Então a janela deve ser válida

  Cenário: Uma janela inválida não pode ter disponível negativo
    Dado uma janela diária com limite 10
    Quando disponível for -1, reservado 0 e consumido 0
    Então a janela deve ser rejeitada

  Cenário: Uma janela inválida não pode ter soma diferente do limite
    Dado uma janela diária com limite 10
    Quando disponível for 8, reservado 2 e consumido 1
    Então a janela deve ser rejeitada

  Cenário: O processType monta a chave do parâmetro
    Dado o processType "parce-compras"
    Quando a chave do parâmetro for montada
    Então a chave deve ser "faturar.rateLimit.parce-compras"

  Cenário: Status possíveis da reserva
    Quando eu consultar os status possíveis da reserva
    Então os status devem ser "PENDING", "CONFIRMED", "CANCELED" e "EXPIRED"

  Cenário: Configuração de rate limit contém os limites diário, semanal e mensal
    Quando eu criar uma configuração habilitada com limite diário 10, semanal 20 e mensal 30
    Então a configuração deve estar habilitada
    E os limites devem ser diário 10, semanal 20 e mensal 30

  Cenário: Reserva pendente representa uma reserva criada
    Quando eu criar uma reserva pendente de 1 unidade para o processType "parce-compras"
    Então a reserva deve ter status "PENDING"
    E o valor reservado deve ser 1

  Cenário: Decisão de rate limit permite postagem sem reserva
    Quando uma decisão permitir postagem sem reservationId por "RATE_LIMIT_DISABLED"
    Então a decisão deve permitir a postagem
    E não deve conter reservationId
