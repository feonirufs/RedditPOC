package app.re_eddit

import app.re_eddit.domain.entity.Comment
import app.re_eddit.domain.entity.Post
import app.re_eddit.domain.entity.PostFullPage

object PostFactory {
    val onePost: List<Post>
        get() = listOf(
            Post(
                title = "Live Thread sobre o Corona Virus no Brasil!",
                subredditNamePrefixed = "r/brasil",
                body = "",
                downs = 0,
                ups = 526,
                commentsCount = 648,
                author = "Donnutz",
                createdUtc = 1584466881,
                created = 1584495681,
                url = "/r/brasil/comments/fk9gfm/live_thread_sobre_o_corona_virus_no_brasil/.json",
                thumbnail = "https://www.reddit.com/live/14n9hibi1rcka/",
                after = "t3_hxlbfk"
            )
        )

    val threePosts: List<Post>
        get() = listOf(
            Post(
                title = "Live Thread sobre o Corona Virus no Brasil!",
                subredditNamePrefixed = "r/brasil",
                body = "",
                downs = 0,
                ups = 526,
                commentsCount = 648,
                author = "Donnutz",
                createdUtc = 1584466881,
                created = 1584495681,
                url = "/r/brasil/comments/fk9gfm/live_thread_sobre_o_corona_virus_no_brasil/.json",
                thumbnail = "https://www.reddit.com/live/14n9hibi1rcka/",
                after = ""
            ),
            Post(
                title = "Que podcasts você está ouvindo? - 24.07.2020",
                subredditNamePrefixed = "r/brasil",
                body = "Reservado para *podcasts*. Quais podcasts você anda ouvindo? Compartilhe com a gente!",
                downs = 0,
                ups = 1,
                commentsCount = 20,
                author = "AutoModerator",
                createdUtc = 1595599290,
                created = 1595628090,
                url = "/r/brasil/comments/hx2amb/que_podcasts_você_está_ouvindo_24072020/.json",
                thumbnail = "https://www.reddit.com/r/brasil/comments/hx2amb/que_podcasts_você_está_ouvindo_24072020/",
                after = ""
            ),
            Post(
                title = "Mais um dia normal por aqui.",
                subredditNamePrefixed = "r/brasil",
                body = "",
                downs = 0,
                ups = 1403,
                commentsCount = 72,
                author = "CyberAsimov",
                createdUtc = 1595676987,
                created = 1595705787,
                url = "/r/brasil/comments/hxlbfk/mais_um_dia_normal_por_aqui/.json",
                thumbnail = "https://v.redd.it/wig4aicdpzc51",
                after = "t3_hxlbfk"
            )
        )

    val fullPostWithComments: PostFullPage
        get() = PostFullPage(
            post = Post(
                title = "Que podcasts você está ouvindo? - 24.07.2020",
                subredditNamePrefixed = "r/brasil",
                body = "Reservado para *podcasts*. Quais podcasts você anda ouvindo? Compartilhe com a gente!",
                downs = 0,
                ups = 1,
                commentsCount = 20,
                author = "AutoModerator",
                createdUtc = 1595599290,
                created = 1595628090,
                url = "/r/brasil/comments/hx2amb/que_podcasts_você_está_ouvindo_24072020/.json",
                thumbnail = "https://www.reddit.com/r/brasil/comments/hx2amb/que_podcasts_você_está_ouvindo_24072020/",
                after = ""
            ),
            comments = listOf(
                Comment(
                    type = 1,
                    author = "AlgunNameDeUsuario",
                    ups = 11,
                    downs = 0,
                    body = "Eu não sou brasileiro mas estou aprendendo português com os podcasts.\n" +
                            "\n" +
                            "    Eu ouço \"dorma com essa\" e eu gosto muito porque é muito corto e fácil de comprender.\n" +
                            "\n" +
                            "    Já vou escutar o Podcast \"O assunto\" e vou lhes dizer como é, se é bom ou não.",
                    createdUtc = 1595605254
                ),
                Comment(
                    type = 1,
                    author = "Henrymlsan",
                    ups = 9,
                    downs = 0,
                    body = "Eu ouço Foro de Teresina, é muito bom com alguns jornalistas da revista Piauí. É um podcast sobre política brasileira e algumas atualidades. Recomendo muito!",
                    createdUtc = 1595613093
                ),
                Comment(
                    type = 2,
                    author = "torre_de_isaias",
                    ups = 2,
                    downs = 0,
                    body = "Resumo obrigatório da semana na política brasileira, toda sexta ou sábado.",
                    createdUtc = 1595685337
                ),
                Comment(
                    type = 2,
                    author = "viniciusgusmao",
                    ups = 1,
                    downs = 0,
                    body = "Se eu fosse outra pessoa, gostaria de ser o Fernando de Barros e Silva.",
                    createdUtc = 1595697179
                ),
                Comment(
                    type = 1,
                    author = "Dr_Ousiris",
                    ups = 5,
                    downs = 0,
                    body = "Comecei a ouvir DUNCAN TRUSSEL FAMILY HOUR, do criador de midnight gospel.\n" +
                            "\n" +
                            "    Alguns episódios tão profundos quanto a série, alguns com piadas bobas, mas todos valem a pena.\n" +
                            "\n" +
                            "    Gosto mto como ele fala abertamente sobre depressão e crises de ansiedade e como lidar com elas",
                    createdUtc = 1595610235
                ),
                Comment(
                    type = 2,
                    author = "LittleBellsprout",
                    ups = 4,
                    downs = 0,
                    body = "Queria ser mais avançado no inglês pra poder acompanhar",
                    createdUtc = 1595612006
                )
            )
        )
}