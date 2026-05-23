package school.sptech.exerciciotestesunitariosspring.dto.emprestimo;

public class PrazoMedioResponseDto {

    private Double mediaDias;
    private Long totalEmprestimosDevolvidos;

    public PrazoMedioResponseDto() {}

    public PrazoMedioResponseDto(Double mediaDias, Long totalEmprestimosDevolvidos) {
        this.mediaDias = mediaDias;
        this.totalEmprestimosDevolvidos = totalEmprestimosDevolvidos;
    }

    public Double getMediaDias() {
        return mediaDias;
    }

    public void setMediaDias(Double mediaDias) {
        this.mediaDias = mediaDias;
    }

    public Long getTotalEmprestimosDevolvidos() {
        return totalEmprestimosDevolvidos;
    }

    public void setTotalEmprestimosDevolvidos(Long totalEmprestimosDevolvidos) {
        this.totalEmprestimosDevolvidos = totalEmprestimosDevolvidos;
    }
}
