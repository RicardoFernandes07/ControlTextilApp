package com.example.controltextil;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
public class EditTimeDialogFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Crie e retorne o diálogo de edição aqui
        // Por exemplo, você pode usar um AlertDialog.Builder para criar o diálogo
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Editar horário")
                .setMessage("Edite o horário para " + getArguments().getString("dayOfWeek"))
                .setPositiveButton("OK", (dialog, which) -> {
                    // Lógica para salvar o horário editado
                })
                .setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        return builder.create();
    }
}
