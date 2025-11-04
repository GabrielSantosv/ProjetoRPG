import java.text.Normalizer;

public class StringNormalizer {
    
    /**
     * Normaliza uma string removendo acentos, convertendo para minúsculas
     * e removendo espaços extras.
     * 
     * @param texto String a ser normalizada
     * @return String normalizada (sem acentos, minúsculas, sem espaços extras)
     */
    public static String normalizar(String texto) {
        if (texto == null || texto.isEmpty()) {
            return "";
        }
        
        // Remove acentos
        String semAcentos = Normalizer.normalize(texto, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "");
        
        // Converte para minúsculas e remove espaços extras
        return semAcentos.toLowerCase().trim().replaceAll("\\s+", " ");
    }
    
    /**
     * Gera um ID canônico a partir de um nome, substituindo espaços por underscores
     * e removendo acentos.
     * 
     * @param nome Nome para gerar o ID
     * @return ID canônico (sem acentos, minúsculas, com underscores)
     */
    public static String gerarId(String nome) {
        if (nome == null || nome.isEmpty()) {
            return "";
        }
        
        String normalizado = normalizar(nome);
        return normalizado.replace(" ", "_");
    }
    
    /**
     * Compara duas strings de forma normalizada (ignorando acentos e diferenças de caixa).
     * 
     * @param str1 Primeira string
     * @param str2 Segunda string
     * @return true se as strings são equivalentes após normalização
     */
    public static boolean equalsNormalizado(String str1, String str2) {
        if (str1 == null && str2 == null) return true;
        if (str1 == null || str2 == null) return false;
        
        return normalizar(str1).equals(normalizar(str2));
    }
}
