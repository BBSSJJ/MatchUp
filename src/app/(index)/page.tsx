import Image from "next/image";
import Link from "next/link";
import styles from "./styles.module.css";
import { RIOT_API_KEY } from "../ui/summoner-info";
import Champion from "../ui/champion";

export const metadata = {
  title: "Today free Champs",
};

const API_FOR_CHAMPION = "https://kr.api.riotgames.com/lol/platform/v3/champion-rotations";


// 오늘의 무료 챔피언 - 20가지
async function getFreeChampions() {
  const response = await fetch(API_FOR_CHAMPION,
    {
      headers: { "X-Riot-Token": `${RIOT_API_KEY}` }
    })
  return response.json();
}

// 여기가 메인페이지 - root segment 
export default async function HomePage() {
  // const movies = await getMovies();
  const championImgUrl = "https://ddragon.leagueoflegends.com/cdn/img/champion/loading/"
  const champions :any = {'266': 'Aatrox', '103': 'Ahri', '84': 'Akali', '166': 'Akshan', '12': 'Alistar', '32': 'Amumu', '34': 'Anivia', '1': 'Annie', '523': 'Aphelios', '22': 'Ashe', '136': 'AurelionSol', '268': 'Azir', '432': 'Bard', '200': 'Belveth', '53': 'Blitzcrank', '63': 'Brand', '201': 'Braum', '233': 'Briar', '51': 'Caitlyn', '164': 'Camille', '69': 'Cassiopeia', '31': 'Chogath', '42': 'Corki', '122': 'Darius', '131': 'Diana', '119': 'Draven', '36': 'DrMundo', '245': 'Ekko', '60': 'Elise', '28': 'Evelynn', '81': 'Ezreal', '9': 'Fiddlesticks', '114': 'Fiora', '105': 'Fizz', '3': 'Galio', '41': 'Gangplank', '86': 'Garen', '150': 'Gnar', '79': 'Gragas', '104': 'Graves', '887': 'Gwen', '120': 'Hecarim', '74': 'Heimerdinger', '910': 'Hwei', '420': 'Illaoi', '39': 'Irelia', '427': 'Ivern', '40': 'Janna', '59': 'JarvanIV', '24': 'Jax', '126': 'Jayce', '202': 'Jhin', '222': 'Jinx', '145': 'Kaisa', '429': 'Kalista', '43': 'Karma', '30': 'Karthus', '38': 'Kassadin', '55': 'Katarina', '10': 'Kayle', '141': 'Kayn', '85': 'Kennen', '121': 'Khazix', '203': 'Kindred', '240': 'Kled', '96': 'KogMaw', '897': 'KSante', '7': 'Leblanc', '64': 'LeeSin', '89': 'Leona', '876': 'Lillia', '127': 'Lissandra', '236': 'Lucian', '117': 'Lulu', '99': 'Lux', '54': 'Malphite', '90': 'Malzahar', '57': 'Maokai', '11': 'MasterYi', '902': 'Milio', '21': 'MissFortune', '62': 'MonkeyKing', '82': 'Mordekaiser', '25': 'Morgana', '950': 'Naafiri', '267': 'Nami', '75': 'Nasus', '111': 'Nautilus', '518': 'Neeko', '76': 'Nidalee', '895': 'Nilah', '56': 'Nocturne', '20': 'Nunu', '2': 'Olaf', '61': 'Orianna', '516': 'Ornn', '80': 'Pantheon', '78': 'Poppy', '555': 'Pyke', '246': 'Qiyana', '133': 'Quinn', '497': 'Rakan', '33': 'Rammus', '421': 'RekSai', '526': 'Rell', '888': 'Renata', '58': 'Renekton', '107': 'Rengar', '92': 'Riven', '68': 'Rumble', '13': 'Ryze', '360': 'Samira', '113': 'Sejuani', '235': 'Senna', '147': 'Seraphine', '875': 'Sett', '35': 'Shaco', '98': 'Shen', '102': 'Shyvana', '27': 'Singed', '14': 'Sion', '15': 'Sivir', '72': 'Skarner', '901': 'Smolder', '37': 'Sona', '16': 'Soraka', '50': 'Swain', '517': 'Sylas', '134': 'Syndra', '223': 'TahmKench', '163': 'Taliyah', '91': 'Talon', '44': 'Taric', '17': 'Teemo', '412': 'Thresh', '18': 'Tristana', '48': 'Trundle', '23': 'Tryndamere', '4': 'TwistedFate', '29': 'Twitch', '77': 'Udyr', '6': 'Urgot', '110': 'Varus', '67': 'Vayne', '45': 'Veigar', '161': 'Velkoz', '711': 'Vex', '254': 'Vi', '234': 'Viego', '112': 'Viktor', '8': 'Vladimir', '106': 'Volibear', '19': 'Warwick', '498': 'Xayah', '101': 'Xerath', '5': 'XinZhao', '157': 'Yasuo', '777': 'Yone', '83': 'Yorick', '350': 'Yuumi', '154': 'Zac', '238': 'Zed', '221': 'Zeri', '115': 'Ziggs', '26': 'Zilean', '142': 'Zoe', '143': 'Zyra'}
  const freeChampionId = await getFreeChampions();
  const freeChampionIdArray = freeChampionId['freeChampionIds'] // number[]
  const freeChampionNames = freeChampionIdArray.map((id :number) => {
    return champions[`${id}`]
  })
  // console.log(freeChampionNames);

  return (
    <div>
      <p className={styles.title}>this week free-to-play champions</p>
      <div className={styles.container}>
        
        {
          // const championName = champions[`${championId}`];
          // const championImgUrl = "https://ddragon.leagueoflegends.com/cdn/img/champion/loading/";
          freeChampionNames.map((championId : string) => (
            <Champion
              key={championId}
              poster_path={championImgUrl + championId + '_0.jpg'}
              title={championId}
            />
        ))}
      </div>
    </div>
  );
}
