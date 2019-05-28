package br.com.app07_partiu.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;

import java.io.IOException;

import br.com.app07_partiu.Model.Comanda;
import br.com.app07_partiu.Model.Item;
import br.com.app07_partiu.Model.Usuario;
import br.com.app07_partiu.Network.ComandaNetwork;
import br.com.app07_partiu.Network.Connection;
import br.com.app07_partiu.R;

import static br.com.app07_partiu.Activity.CodigoComandaClienteActivity.CLIENTE;
import static br.com.app07_partiu.Activity.CodigoComandaClienteActivity.COMANDA;
import static br.com.app07_partiu.Activity.CodigoComandaClienteActivity.ITENS;

public class ComandaClienteActivity extends AppCompatActivity {

    private Usuario cliente;
    private Comanda comanda;
    private Item[] itens;

    Context context;
    Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comanda_cliente_activiy);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        context = this;
        intent = this.getIntent();

        cliente = (Usuario) intent.getSerializableExtra(CLIENTE);
        comanda = (Comanda) intent.getSerializableExtra(COMANDA);
        itens = (Item[]) intent.getSerializableExtra(ITENS);
    }


    private void selecionarPedido(final Item item) {
        if (Connection.isConnected(this)) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {

                                final String resultadoSelecionar = ComandaNetwork.selecionarPedido(Connection.URL, item.getIdPedido(), cliente.getId());
                                if (resultadoSelecionar != "") {
                                    itens = ComandaNetwork.buscarPedidosComanda(Connection.URL, comanda.getId());
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            switch (resultadoSelecionar) {
                                                case "selecionado": // Selecionou e nenhum outro usuario selecionou
                                                    Toast.makeText(context, "Item Selecionado!", Toast.LENGTH_LONG).show();
                                                    break;
                                                case "cancelado": // Não selecionou, item cancelado
                                                    Toast.makeText(context, "O item não existe masi!", Toast.LENGTH_LONG).show();
                                                    break;
                                                case "dividindo": // Selecionou, retornou Nomes de usuários com quem vai dividindo
                                                    Toast.makeText(context, "Item Selecionado e sendo Dividido!", Toast.LENGTH_LONG).show();
                                                    break;
                                                default:
                                                    Toast.makeText(context, "Deu ruim!", Toast.LENGTH_LONG).show();
                                                    Log.d("TESTES", "Erro selecionarPedido (dentro do SWITCH");
                                                    break;

                                            }
//                                        getPedidos();
                                        }
                                    });
                                }else{
                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            Toast.makeText(context, "Deu ruim! Ver LOG", Toast.LENGTH_LONG).show();
                                            Log.d("TESTES", "Erro selecionarPedido (no ELSE)");
                                        }
                                    });
                                }
                            } catch (IOException e) {
                                Log.e("TESTES", "IOException selecioanarPedidos'");
                                e.printStackTrace();
                            }

//                            catch (JSONException e) {
//                                e.printStackTrace();
//                                //TODO ver oq acontece quando 2 pegam ao msm tempo
//                                Log.e("TESTES", "--");
//                                runOnUiThread(new Runnable() {
//                                    public void run() {
//                                        Toast.makeText(context, "--", Toast.LENGTH_LONG).show();
//                                    }
//                                });
//                            }
                        }
                    }).start();
        }
    }

    private void getPedidos() {
        if (Connection.isConnected(this)) {
            new Thread(
                    new Runnable() {
                        @Override
                        public void run() {
                            try {

                                String dataComandaAtualizacao = "";
                                String novaData = ComandaNetwork.getDataAtualizacaoComanda(Connection.URL, comanda.getId());

                                if (!novaData.equals(dataComandaAtualizacao)) {
                                    itens = ComandaNetwork.buscarPedidosComanda(Connection.URL, comanda.getId());
                                    dataComandaAtualizacao = novaData;

                                    runOnUiThread(new Runnable() {
                                        public void run() {
                                            reloadPedidos();
                                        }
                                    });
                                }
                            } catch (IOException e) {
                                Log.e("TESTES", "IOException getComandaPedidos'");
                                e.printStackTrace();
                            }
                        }
                    }).start();
        }
    }

    private void reloadPedidos() {
        //TODO implementar intervalo
        getPedidos();
    }


}
