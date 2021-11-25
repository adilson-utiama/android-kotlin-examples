package com.asuprojects.kotlincustomcomponents.fragments.transitions.sharedactivity

import com.asuprojects.kotlincustomcomponents.R
import java.lang.StringBuilder

class ProductCreationHelper {

    companion object {

        fun getProducts(): MutableList<Product>{
            return mutableListOf(
                Product(
                    R.drawable.a1_motherboard_gigabyte_b365m,
                    "Motherboard Gigabyte B365M",
                    "Intel B365 Ultra Durable motherboard with GIGABYTE 8118 Gaming LAN, PCIe Gen3 x4 M.2, 7 colors RGB LED strips support, Anti-Sulfur Resistor, Smart Fan 5, DualBIOS, CEC 2019 ready",
                    StringBuilder().append("- Supports 9th and 8th Gen Intel® Core Processors").append("\n")
                        .append("- Dual Channel Non-ECC Unbuffered DDR4").append("\n")
                        .append("- New Hybrid Digital PWM Design").append("\n")
                        .append("- High Quality Audio Capacitors and Audio Noise Guard with LED Trace Path Lighting").append("\n")
                        .append("- Ultra-Fast M.2 with PCIe Gen3 X4 & SATA interface").append("\n")
                        .append("- RGB FUSION supports RGB LED strips in 7 colors").append("\n")
                        .append("- GIGABYTE Exclusive 8118 Gaming LAN with Bandwidth Management").append("\n")
                        .append("- CEC 2019 Ready, Save the Power as Easy as One Click").append("\n")
                        .append("- Smart Fan 5 features Multiple Temperature Sensors and Hybrid Fan Headers with FAN STOP").append("\n")
                        .append("- Anti-Sulfur Resistors Design").append("\n")
                        .append("- Ultra Durable 15KV Surge LAN Protection").append("\n")
                        .append("- Intel® Optane Memory Ready").append("\n")
                        .append("- GIGABYTE UEFI DualBIOS").append("\n").toString(),
                    700.0
                ),
                Product(
                    R.drawable.a2_intel_core_i59400,
                    "Processor Intel Core I5-9400 2.9Ghz LGA1151",
                    "Processador Intel Core i5-9400 Coffee Lake - 9MB 2.9 Ghz - LGA 1151Com esse processador inovador e incrível você desfruta ao máximo o verdadeiro potencial do seu computador e da mais pura velocidade. Maximize o seu desempenho seja trabalhando, jogando, navegando ou assistindo o seu filme preferido, com esse processador você pode tudo!",
                    StringBuilder().append("- Supports 9th and 8th Gen Intel® Core Processors").append("\n")
                        .append("- 9ª geração de processadores Intel® Core™ i5").append("\n")
                        .append("- Número de núcleos: 6").append("\n")
                        .append("- Numero de threads: 6").append("\n")
                        .append("- Frequência baseada em processador: 2.90 GHz").append("\n")
                        .append("- Frequência turbo max: 4.10 GHz").append("\n")
                        .append("- Cache: 9 MB Intel Smart Cache").append("\n")
                        .append("- Tamanho máximo de memória: 128 GB").append("\n")
                        .append("- Tipos de memória: DDR4-2666").append("\n")
                        .append("- Numero máximo de canais de memória: 2").append("\n")
                        .append("- Gráficos UHD Intel® 630").append("\n")
                        .append("- Memória gráfica de vídeo: 64 GB").append("\n")
                        .append("- Suporte para DirectX: 12").append("\n")
                        .append("- Suporte para OpenGL: 4.5").append("\n").toString(),
                    1500.0
                ),
                Product(
                    R.drawable.a3_nvidia_geforce_gtx_1050_ti,
                    "Nvidia GeForce GTX1050 TI",
                    "Todo mundo merece jogar de um jeito incrível. Por isso, criamos a rápida e potente GeForce GTX 1050. Agora, você pode transformar seu PC em uma verdadeira plataforma de games, com a tecnologia da NVIDIA Pascal — a mais avançada arquitetura de placa de vídeo já criada.",
                    StringBuilder().append("- NVIDIA CUDA Cores: 768").append("\n")
                        .append("- Clock Basico: 129Mhz").append("\n")
                        .append("- Memoria: GDDR5 4GB").append("\n")
                        .append("- Interface de memoria: 128bit").append("\n")
                        .append("- DirectX: 12").append("\n")
                        .append("- OpenGL: 4.5").append("\n")
                        .append("- Resolucao Maxima: 7680x4320 - 60Hz").append("\n").toString(),
                    1700.0
                ),
                Product(
                    R.drawable.a4_memoria_corsair_vengeance_lpx_8gb,
                    "Memoria Corsair Vengeance LPX 8GB",
                    "O heatspreader é feito de alumínio puro para dissipação de calor mais rápida, e o PCB de oito camadas ajuda a controlar o calor e proporciona um espaço superior para overclocking.",
                    StringBuilder().append("- Capacidade: 8GB").append("\n")
                        .append("- Velocidade: 2400 Mhz").append("\n")
                        .append("- Pinagem: 288-pin").append("\n")
                        .append("- Timing: 16-16-16-39").append("\n")
                        .append("- Cas Latência: 16").append("\n")
                        .append("- Tecnologia: DIMM").append("\n")
                        .append("- Tipo: DDR4 SDRAM").append("\n").toString(),
                    295.0
                ),
                Product(
                    R.drawable.a5_gabinete_thermaltake_view_21_tg_preto_ring,
                    "Gabinete Thermaltake View 21 TG Preto Ring",
                    "O gabinete mid tower View 21 Tempered Glass Edition possui duas janelas de vidro temperado de 4 mm de espessura (esquerda e direita), um fan traseiro pré-instalado de 120 mm com filtros removíveis, tampa de PSU de comprimento total e excelente sistema de ventilação.",
                    StringBuilder().append("- Dimensões: 492mm x 208mm x 471mm").append("\n")
                        .append("- Painel Lateral: Vidro Temperado de 4mm x 2 (Esquerda e Direita)").append("\n")
                        .append("- Cor: Exterior e Interior: Preto").append("\n")
                        .append("- Sistema de Refrigeração: Parte traseira, Ventoinha 120mm x 120mm x 25mm").append("\n")
                        .append("- Baias: Acessível 2 x 2,5 2 x 3,5 ou 2 x 2,5").append("\n")
                        .append("- Slots de Expansão: 7").append("\n")
                        .append("- Placa Mãe Suportada: Mini ITX, Micro ATX, ATX").append("\n")
                        .append("- Painel Frontal: 2 x USB 3.0 - 1 x HD Audio").append("\n").toString(),
                    545.0
                ),
                Product(
                    R.drawable.a6_gigabyte_radeon_rx_580_windforce_8gb_gddr5,
                    "Gigabyte Radeon RX 580 Windforce 8GB RGB GDDR5",
                    "The WINDFORCE 2X cooling system features 3 pure copper composite heat-pipes, heat-pipe direct touch GPU, 90mm unique blade fan design, 3D active fan with LED indicators, stylish metal back plate, together delivering an effective heat dissipation capacity for higher performance at lower temperatures.",
                    StringBuilder().append("- Memory Clock: 8000 MHz").append("\n")
                        .append("- Memory Size: 8 GB").append("\n")
                        .append("- Memory Type: GDDR5").append("\n")
                        .append("- Memory Bus: 256 bit").append("\n")
                        .append("- Digital max resolution: 7680x4320").append("\n")
                        .append("- DirectX: 12").append("\n")
                        .append("- OpenGL: 4.5").append("\n").toString(),
                    1450.0
                )
            )


        }
    }
}