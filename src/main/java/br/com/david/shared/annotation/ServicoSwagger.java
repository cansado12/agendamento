package br.com.david.shared.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ServicoSwagger {
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Salvar servico",
            description = "Salva o servico no banco de dados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servico salvo com sucesso"),
            @ApiResponse(responseCode = "400", description = "Servico invalido"),
            @ApiResponse(responseCode = "404", description = "Nao autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerSalvarServico {
    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Lista servicos",
            description = "Lista todos os servicos"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de servicos encontrado"),
            @ApiResponse(responseCode = "400", description = "Nenhum servico encontrado"),
            @ApiResponse(responseCode = "404", description = "Nao autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerListarServicos {
    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Buscar por id",
            description = "Busca servico por id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servico encontrado"),
            @ApiResponse(responseCode = "400", description = "Servico invalido"),
            @ApiResponse(responseCode = "422", description = "regra violada"),
            @ApiResponse(responseCode = "404", description = "Nao autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerBuscarServicoPorId {
    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Deleta servico",
            description = "Deleta o servico no banco de dados"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Servico deletado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Servico invalido"),
            @ApiResponse(responseCode = "422", description = "regra violada"),
            @ApiResponse(responseCode = "404", description = "Nao autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerDeletarServico {
    }



}
