package br.com.david.shared.annotation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class ClienteSwagger {
    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Cria um cliente",
            description = "Criar um cliente no sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Cliente invalido"),
            @ApiResponse(responseCode = "422", description = "regra violada"),
            @ApiResponse(responseCode = "404", description = "Nao autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerCreateCliente {
    }



    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "lista clientes",
            description = "Lista todos os clientes do sistema"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de cliente encontrada"),
            @ApiResponse(responseCode = "400", description = "Clientes nao encotnrados"),
            @ApiResponse(responseCode = "422", description = "regra violada"),
            @ApiResponse(responseCode = "404", description = "Nao autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerListarClientes {

    }


    @Target(ElementType.METHOD)
    @Retention(RetentionPolicy.RUNTIME)

    @Operation(
            summary = "Busca cliente",
            description = "Busca cliente pelo id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cliente encontrado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Cliente nao invalido"),
            @ApiResponse(responseCode = "422", description = "regra violada"),
            @ApiResponse(responseCode = "404", description = "Nao autorizado"),
            @ApiResponse(responseCode = "409", description = "Conflito nos dados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    public @interface SwaggerBuscarCliente {

    }














}
