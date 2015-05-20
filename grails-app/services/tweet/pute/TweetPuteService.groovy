package tweet.pute

import grails.transaction.Transactional

@Transactional
class TweetPuteService {

    List listCollaborators(Class domainClass, params) {
        List collaborators = []
        if (params.sort == 'tweets') {
            String ascOrDesc = params.order == 'asc' ? 'asc' : 'desc'
            String lowerName = domainClass.simpleName.toLowerCase()
            collaborators = domainClass.executeQuery("""SELECT $lowerName
                from ${domainClass.simpleName} $lowerName
                order by size(${lowerName}.tweets) $ascOrDesc """, [:], params)
        }
        else {collaborators = domainClass.list(params)}
        return collaborators
    }
}
