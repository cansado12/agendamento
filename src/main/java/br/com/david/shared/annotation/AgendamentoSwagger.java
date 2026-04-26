package br.com.david.shared.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class AgendamentoSwagger {
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Inserir a agenda",
            description = "Insere o servico a agenda"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servico agendado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Agendamento invalido"),
            @ApiResponse(responseCode = "404", description = "Nao autorizado"),
            @ApiResponse(responseCode = "422", description = "regra violada"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerInserirAgendamento {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Listar Agendamentos",
            description = "Lista todos os servicos agendados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista encontrada"),
            @ApiResponse(responseCode = "400", description = "Lista nao encontrada"),
            @ApiResponse(responseCode = "404", description = "Nao autorizado"),
            @ApiResponse(responseCode = "422", description = "regra violada"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerListarAgendamentos {
    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Listar por id",
            description = "Lista agendamento por id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servico encontrado"),
            @ApiResponse(responseCode = "400", description = "Agendamento invalido"),
            @ApiResponse(responseCode = "422", description = "regra violada"),
            @ApiResponse(responseCode = "404", description = "Nao autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerListarAgendamentoPorId {

    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Cancelar agendamento",
            description = "cancela o agendamento"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servico cancelado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Agendamento invalido"),
            @ApiResponse(responseCode = "422", description = "regra violada"),
            @ApiResponse(responseCode = "404", description = "Nao autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerCancelarAgendamento {
    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Atualiza agenda",
            description = "Atualiza servico agendado"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servico Atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Agendamento invalido"),
            @ApiResponse(responseCode = "422", description = "regra violada"),
            @ApiResponse(responseCode = "404", description = "Nao autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerAtualizarAgendamento {
    }

    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Buscar cliente agendado",
            description = "Busca cliente agendado pelo id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "400", description = "Cliente invalido"),
            @ApiResponse(responseCode = "422", description = "regra violada"),
            @ApiResponse(responseCode = "404", description = "Nao autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerBuscarClienteAgendado {
    }






}
