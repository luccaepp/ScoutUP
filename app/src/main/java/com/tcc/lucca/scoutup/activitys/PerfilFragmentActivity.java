package com.tcc.lucca.scoutup.activitys;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.lucca.scoutup.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.tcc.lucca.scoutup.gerenciar.AmigoListAdapter;
import com.tcc.lucca.scoutup.gerenciar.GrupoDAO;
import com.tcc.lucca.scoutup.gerenciar.ListViewAdapter;
import com.tcc.lucca.scoutup.gerenciar.UsuarioDAO;
import com.tcc.lucca.scoutup.model.Amigo;
import com.tcc.lucca.scoutup.model.Grupo;
import com.tcc.lucca.scoutup.model.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PerfilFragmentActivity extends Fragment {


    private UsuarioDAO usuarioDAO = UsuarioDAO.getInstance();
    private GrupoDAO grupoDAO = GrupoDAO.getInstance();


    private FirebaseUser firebaseUser;
    private Usuario usuarioDatabase;
    private Grupo grupoDatabase;

    private ListView listViewInfo;
    private ListView listViewAmigos;
    private ListView listViewEspec;

    private Button btnLogout;
    private Button btnEditar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        initComponents(container);
        return inflater.inflate(R.layout.activity_perfil_fragment, container, false);
    }

    private void initComponents(ViewGroup container) {
        listViewInfo = container.findViewById(R.id.listViewInformacoes);
        listViewAmigos = container.findViewById(R.id.listViewAmigos);
        listViewEspec = container.findViewById(R.id.listViewEspecialidades);

    }

    private void initData() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String uid = firebaseUser.getUid();
        Query query = usuarioDAO.getClassFromDatabase(uid);
        final PerfilFragmentActivity self = this;


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Usuario user = dataSnapshot.getValue(Usuario.class);
                self.setUsuarioDatabase(user);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void atualizarInfoPerfil() {


        String uid = usuarioDatabase.getGrupo();
        Query query = grupoDAO.getClassFromDatabase(uid);
        final PerfilFragmentActivity self = this;

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Grupo grupo = (dataSnapshot.getValue(Grupo.class));

                self.setGrupoDatabase(grupo);

                carregarUsuario();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void carregarUsuario() {
        List<String> info = new ArrayList<>();
        info.add(usuarioDatabase.getNome());
        info.add(usuarioDatabase.getEmail());

        info.add(grupoDatabase.getNome());
        info.add(usuarioDatabase.getSecao().getNome());


        listViewInfo = getView().findViewById(R.id.listViewInformacoes);
        ListViewAdapter adapter = new ListViewAdapter(getContext(), info);
        listViewInfo.setAdapter(adapter);



    }

    public void setUsuarioDatabase(Usuario usuarioDatabase) {
        this.usuarioDatabase = usuarioDatabase;
        atualizarInfoPerfil();
        atualizarAmigos();
        atualizarEspecialidades();
    }

    public void setGrupoDatabase(Grupo grupoDatabase) {
        this.grupoDatabase = grupoDatabase;

    }

    private void atualizarAmigos() {

        listViewAmigos = getView().findViewById(R.id.listViewAmigos);

        HashMap<String, Amigo> amigosMap = usuarioDatabase.getAmigos();

        List<Amigo> amigos = new ArrayList<>(amigosMap.values());


        AmigoListAdapter adapter = new AmigoListAdapter(getContext(), amigos);

        listViewAmigos.setAdapter(adapter);

    }

    private void atualizarEspecialidades() {

    }
}
