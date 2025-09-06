package com.ribeiro.poc.service;

import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.ByteArrayOutputStream;

import org.thymeleaf.context.Context;
import java.util.Locale;
import java.util.Map;

@Service
public class DocumentoVisualizacaoService {

    private final SpringTemplateEngine templateEngine;

    public DocumentoVisualizacaoService(SpringTemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    // 🔹 Gera o HTML a partir de um template Thymeleaf e variáveis
    public String gerarHtml(String nomeTemplate, Map<String, Object> modelo) {
        Context ctx = new Context(Locale.forLanguageTag("pt-BR"));
        modelo.forEach(ctx::setVariable); // adiciona cada variável no contexto
        return templateEngine.process(nomeTemplate, ctx); // processa o template
    }

    // 🔹 Converte esse HTML para PDF
    public byte[] htmlParaPdf(String html) {
        try (var out = new ByteArrayOutputStream()) {
            var builder = new com.openhtmltopdf.pdfboxout.PdfRendererBuilder();
            builder.withHtmlContent(html, null);
            builder.toStream(out);
            builder.run();
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar PDF", e);
        }
    }
}
