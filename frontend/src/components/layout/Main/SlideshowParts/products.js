import lap1 from "../../../../assets/lap1.png";
import lap2 from "../../../../assets/lap2.png";
import item5 from "../../../../assets/item5.png";
import ram1 from "../../../../assets/ram1.png";

export const products = [
    {
        id: 1,
        name: "Pulsar eS FS-1 – Chuột không dây eSport",
        price: "5.060.000đ",
        img: lap1,
        variants: [{ img: lap1, active: true }]
    },
    {
        id: 2,
        name: "Pulsar eS PD170 Gaming Mousepad – Lót chuột chơi game esport",
        price: "Từ 1.430.000đ",
        img: lap2
    },
    {
        id: 3,
        name: "Pulsar eS eSports Arm Sleeve – Bao tay chơi game chuyên nghiệp",
        price: "561.000đ",
        img: item5,
        variants: [{ img: item5, active: true }]
    },
    {
        id: 4,
        name: "Pulsar X2N Crazylight – Chuột không dây kèm dongle 8K",
        price: "3.740.000đ",
        img: lap1,
        variants: [
            { img: lap1, crossed: true },
            { img: lap1, active: true },
            { img: lap1 }
        ]
    },
    {
        id: 5,
        name: "Pulsar X2 Crazylight PRX GOLD Edition – Chuột đối xứng kèm dongle 8K",
        price: "3.960.000đ",
        img: ram1,
        tag: "Hết hàng",
        variants: [
            { img: ram1, crossed: true, active: true }
        ]
    },
    {
        id: 6,
        name: "Razer Blackshark V2 Pro – Tai nghe không dây eSport",
        price: "8.690.000đ",
        img: item5,
        tag: "Đặt trước"
    },
];
